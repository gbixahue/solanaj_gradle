package org.p2p.solanaj.rpc;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import okhttp3.*;
import org.p2p.solanaj.rpc.types.RpcRequest;
import org.p2p.solanaj.rpc.types.RpcResponse;
import org.p2p.solanaj.rpc.types.WeightedEndpoint;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RpcClient {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String endpoint;
    private OkHttpClient httpClient;
    private RpcApi rpcApi;
    private WeightedCluster cluster;

    public RpcClient(WeightedCluster cluster) {
        this(cluster, null);
    }

    public RpcClient(WeightedCluster cluster, List<Interceptor> httpInterceptors) {
        this.cluster = cluster;
        createHttpClient(httpInterceptors);
    }

    public RpcClient(Cluster endpoint) {
        this(endpoint.getEndpoint(), null);
    }

    public RpcClient(Cluster endpoint, List<Interceptor> httpInterceptors) {
        this(endpoint.getEndpoint(), httpInterceptors);
    }

    public RpcClient(String endpoint, List<Interceptor> httpInterceptors) {
        this.endpoint = endpoint;
        this.httpClient = createHttpClient(httpInterceptors);
        this.rpcApi = createRpcApi();
    }

    protected OkHttpClient createHttpClient(List<Interceptor> httpInterceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(20, TimeUnit.SECONDS);
        if (httpInterceptors != null) {
            for (Interceptor interceptor : httpInterceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        return builder.build();
    }

    protected RpcApi createRpcApi() {
        return new RpcApi(this);
    }

    public <T> T call(String method, List<Object> params, Class<T> clazz) throws RpcException {
        RpcRequest rpcRequest = new RpcRequest(method, params);

        JsonAdapter<RpcRequest> rpcRequestJsonAdapter = new Moshi.Builder().build().adapter(RpcRequest.class);
        JsonAdapter<RpcResponse<T>> resultAdapter = new Moshi.Builder().build()
                .adapter(Types.newParameterizedType(RpcResponse.class, Type.class.cast(clazz)));

        Request request = new Request.Builder().url(getEndpoint())
                .post(RequestBody.create(rpcRequestJsonAdapter.toJson(rpcRequest), JSON)).build();

        try {
            Response response = httpClient.newCall(request).execute();
            final String result = response.body().string();
            // System.out.println("Response = " + result);
            RpcResponse<T> rpcResult = resultAdapter.fromJson(result);

            if (rpcResult.getError() != null) {
                throw new RpcException(rpcResult.getError().getMessage());
            }

            return (T) rpcResult.getResult();
        } catch (SSLHandshakeException e) {
            this.httpClient = new OkHttpClient.Builder().build();
            throw new RpcException(e.getMessage());
        } catch (IOException e) {
            throw new RpcException(e.getMessage());
        }
    }

    public RpcApi getApi() {
        return rpcApi;
    }

    public String getEndpoint() {
        if (cluster != null) {
            return getWeightedEndpoint();
        }
        return endpoint;
    }

    /**
     * Returns RPC Endpoint based on a list of weighted endpoints
     * Weighted endpoints can be given a integer weight, with higher weights used more than lower weights
     * Total weights across all endpoints do not need to sum up to any specific number
     *
     * @return String RPCEndpoint
     */
    private String getWeightedEndpoint() {
        int currentNumber = 0;
        int randomMultiplier = cluster.endpoints.stream().mapToInt(WeightedEndpoint::getWeight).sum();
        double randomNumber = Math.random() * randomMultiplier;
        String currentEndpoint = "";
        for (WeightedEndpoint endpoint : cluster.endpoints) {
            if (randomNumber > currentNumber + endpoint.getWeight()) {
                currentNumber += endpoint.getWeight();
            } else if (randomNumber >= currentNumber && randomNumber <= currentNumber + endpoint.getWeight()) {
                return endpoint.getUrl();
            }
        }
        return currentEndpoint;
    }

}
