package com.xxc.dev.network.utils;

import com.xxc.dev.network.response.INetworkResult;
import com.xxc.dev.network.response.JsonNetworkResult;
import com.xxc.dev.network.response.NetworkError;
import com.xxc.dev.network.response.StringNetworkResult;
import java.lang.reflect.Type;
import java.util.List;
import org.junit.Test;

public class GenericUtilsTest {

    @Test
    public void getCallBackType() {
        JsonNetworkResult result = new JsonNetworkResult();
        Type jsonType = NetworkGenericUtils.getCallBackType(result);
        System.out.println(jsonType);
        StringNetworkResult stringResult = new StringNetworkResult();
        Type stringType = NetworkGenericUtils.getCallBackType(stringResult);
        System.out.println(stringType);
        System.out.println("".getClass().getName());

        INetworkResult<List<String>> listNetworkResult=new INetworkResult<List<String>>() {
            @Override
            public void onSuccess(Object tag, List<String> strings) {

            }

            @Override
            public void onFailure(NetworkError error) {

            }
        };
        Type listType = NetworkGenericUtils.getCallBackType(listNetworkResult);
        System.out.println(listType);

        INetworkResult<List<NetworkError>> listError=new INetworkResult<List<NetworkError>>() {
            @Override
            public void onSuccess(Object tag, List<NetworkError> networkErrors) {

            }

            @Override
            public void onFailure(NetworkError error) {

            }
        };
        Type listErrorType = NetworkGenericUtils.getCallBackType(listError);
        System.out.println(listErrorType);

        INetworkResult testResult = new TestResult();
        Type testType = NetworkGenericUtils.getCallBackType(testResult);
        System.out.println(testType);

    }
}