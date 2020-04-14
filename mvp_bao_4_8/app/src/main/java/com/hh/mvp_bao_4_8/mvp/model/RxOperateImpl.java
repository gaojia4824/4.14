package com.hh.mvp_bao_4_8.mvp.model;
import android.text.TextUtils;
import android.widget.ProgressBar;

import com.hh.mvp_bao_4_8.app.App;
import com.hh.mvp_bao_4_8.callback.IDataCallBack;
import com.hh.mvp_bao_4_8.callback.ProgressDataCallback;
import com.hh.mvp_bao_4_8.di.component.AppComponent;
import com.hh.mvp_bao_4_8.di.component.DaggerRxOperateComponent;
import com.hh.mvp_bao_4_8.mvp.model.api.ApiService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.http.Body;

public class RxOperateImpl {
    //Rxjava  OkHttp关联的封装类
    @Inject
    ApiService mApiService;
    public RxOperateImpl() {
        //将apiService注入到RxOperateImpl里面
        DaggerRxOperateComponent.builder().
                appComponent(App.daggerAppComponent()).build().
                inject(this);
    }
    /**
     * @param url          get请求的URL地址
     * @param dataCallBack 结果回调(实际上是接口回调)
     * @param <T>          接口回调获取的值
     */
    public <T> void requestData(String url, IDataCallBack<T> dataCallBack) {
        RxSchedulersOperator.retryWhenOperator
                (mApiService.requestData(url)).
                subscribe(getObserver(dataCallBack));
    }


    /**
     * @param url          get请求的url地址
     * @param params       get请求的参数
     * @param dataCallBack get请求的结果回调
     * @param <T>
     */
    public <T> void requestData(String url, Map <String, T> params, IDataCallBack<T> dataCallBack) {
        if (params == null || params.size() == 0)
            requestData(url, dataCallBack);
        else
            RxSchedulersOperator.retryWhenOperator
                    (mApiService.requestData(url, params)).
                    subscribe(getObserver(dataCallBack));
    }


    /**
     * 没有参数的post请求
     *
     * @param url
     * @param dataCallBack
     * @param <T>
     */
    public <T> void requestFormData(String url, IDataCallBack<T> dataCallBack) {
        RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url)).
                subscribe(getObserver(dataCallBack));

    }
    /**
     * 有参数的post请求
     * @param url
     * @param params
     * @param dataCallBack
     * @param <T>
     */
    public <T> void requestFormData(String url, Map<String, T> params, IDataCallBack<T> dataCallBack) {
        if (params == null || params.size() == 0) {
            requestFormData(url, dataCallBack);
        }else {
            RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url, params)).
                    subscribe(getObserver(dataCallBack));
        }
    }
    //只有请求头的post请求
    /**
     * @param url
     * @param headers
     * @param dataCallBack
     * */
    public <T> void requestFormData(String url, HashMap<String,T> headers,IDataCallBack<T> dataCallBack){
        if (headers==null||headers.size()==0){
            requestData(url,dataCallBack);
        }else{
            RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url,headers)).subscribe(getObserver(dataCallBack));
        }
    }
    /**
     * 有请求头并且有参数的post请求
     *
     * @param url
     * @param headers
     * @param params
     * @param dataCallBack
     * @param <T>
     */
    public <T> void requestFormData(String url, Map<String, T> headers, Map<String, T> params, IDataCallBack<T> dataCallBack) {
        if (headers == null || headers.size() == 0)  //请求头为空 但是参数不为空
            requestFormData(url, params, dataCallBack);
        else if ((headers == null || headers.size() == 0) && // 请求头和参数都为空
                (params == null || params.size() == 0)) {
            requestFormData(url, dataCallBack);
        }else if (params==null||params.size()==0){//参数为但是请求头不为空
            requestFormData(url,headers,dataCallBack);
        }else
            //请求头和参数都不为空
            RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url, headers, params)).
                    subscribe(getObserver(dataCallBack));
    }
    //带json串的没有请求头的post请求
    /**
     * @param url
     * @param jsonStr
     * @param dataCallBack
     * @param <T>
     * */
    public <T> void requestFormData(String url,String jsonStr,IDataCallBack<T> dataCallBack){
        if (jsonStr.isEmpty())
            return;
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonStr);
        RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url,requestBody)).subscribe(getObserver(dataCallBack));
    }


    //带请求头的上传json串的post请求
    /**
     * @param  url
     * @param  haders   请求头
     * @param jsonStr  要上传的json串
     * @param dataCallBack  结果回调
     * @param <T>
     * */
    public <T> void requestFormData(String url,Map<String,T> haders,String jsonStr,IDataCallBack<T> dataCallBack){
        if (haders==null||haders.size()==0){
            requestFormData(url, jsonStr, dataCallBack);
        }else{
            if (jsonStr.isEmpty()) {
                return;
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);
            RxSchedulersOperator.retryWhenOperator
                    (mApiService.requestFormData(url, haders, requestBody)).
                    subscribe(getObserver(dataCallBack));

        }
    }

    //
    /**
     * @param url 下载文件的路径
     * @param progressDataCallback   下载结果回调
     * @param isProess   是否带进度
     * @param <T>
     * */
    public <T> void downloadFile(String url, boolean isProess, ProgressDataCallback <T> progressDataCallback){
            if (isProess){
                //带进度条

            }else{
                RxSchedulersOperator.retryWhenOperator(mApiService.downloadFile(url)).subscribe(getObserver(progressDataCallback));
            }
    }
    //单个文件上传
    public <T> void uploadSingleFile(String url, File file, IDataCallBack<T> dataCallBack){
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //第一个参数：某些情况 前端和后台的key名要一致
        MultipartBody.Part part = MultipartBody.Part.createFormData("aFile", file.getName(), requestBody);
        RxSchedulersOperator.retryWhenOperator(mApiService.uploadSingleFile(url,part))
                    .subscribe(getObserver(dataCallBack));

    }
    //单个文件+参数上传  举例：图片+文字
    public <T> void uploadStrFile(String url,File file,String str,IDataCallBack<T> dataCallBack){
        if (TextUtils.isEmpty(str)){        //6.0之后动态权限  file
            uploadSingleFile(url,file,dataCallBack);
        }else{
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("aFile", file.getName(), requestBody);
            RequestBody strBody = RequestBody.create(MediaType.parse("multipart/form-data"), str);
            RxSchedulersOperator.retryWhenOperator(mApiService.uploadStrFile(url,strBody,part)).subscribe(getObserver(dataCallBack));
        }
    }
    /**
     * //多个文件上传方法1
     * */
    public <T> void uploadMultipleFiles(IDataCallBack<T> dataCallBack,String url,File... files){
        MultipartBody.Part[] parts = new MultipartBody.Part[files.length];
        if (files!=null&&files.length>0){
            for (int i=0;i<files.length;i++){
                File file=files[i];
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                parts[i]=part;
            }
        }
            RxSchedulersOperator.retryWhenOperator(mApiService.uploadMultipleFiles(url,parts))
                    .subscribe(getObserver(dataCallBack));

    }
    /**
     * //多个文件上传方法2
     * */
    public <T> void uploadMultipleFiles(String url,IDataCallBack dataCallBack,File... files){
        if (files!=null||files.length>0){
            Map <String, RequestBody> filesMap = new HashMap <>();
            for (File file:files){
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                filesMap.put("file;filename="+file.getName(), requestBody);
            }
            RxSchedulersOperator.retryWhenOperator(mApiService.uploadMultipleFiles(url,filesMap))
                    .subscribe(getObserver(dataCallBack));
        }
    }
    /**
     * //多个文件+键值对 上传1
     * uploadStrFiles
     * */
    public <T> void uploadStrFiles(String url,Map<String,String> params,IDataCallBack<T> dataCallBack,File... files){
        if (files!=null&&files.length>0){
           if (params==null||params.size()==0){ //不带参数上传
               uploadMultipleFiles(url,dataCallBack,files);
           }else {
               FormBody.Builder builder = new FormBody.Builder();
               for (int i = 0; i < params.size(); i++) {
                   Set <String> keys = params.keySet();
                   for (String key:keys){
                       String value = params.get(key);
                       //上传键值对
                       builder.add(key,value);
                   }
               }
               Map <String, RequestBody> filesMap = new HashMap <String,RequestBody>();
               for (File file:files){
                   RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                   //上传多个文件
                   filesMap.put("file;fileName="+file.getName(),requestBody);
               }
               RxSchedulersOperator.retryWhenOperator(mApiService.uploadStrFiles(url,builder.build(),filesMap)).subscribe(getObserver(dataCallBack));
           }
        }
    }
    /**
     * 多个文件+多参数上传
     *
     * */
    public <T> void uploadStrFiles(String url, ArrayList<String> map, IDataCallBack<T> dataCallBack, File... files){
        if (files!=null&&files.length>0){
            if (map==null){
                uploadStrFiles(url,map,dataCallBack,files);
            }else{
                MultipartBody.Part[] parts = new MultipartBody.Part[map.size()];
                int count=0;
                for (String m:map){
                    File file = new File(m);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("heading[]", file.getName());
                    parts[count]=part;
                    count++;
                    RxSchedulersOperator.retryWhenOperator(mApiService.uploadStrFiles(url,requestBody,parts)).subscribe(getObserver(dataCallBack));
                }
            }
        }
    }


    /**文件下载
     * @param url
     * @param dataCallBack
     * */
    public <T> void downloadFile(String  url,IDataCallBack dataCallBack){
            RxSchedulersOperator.retryWhenOperator(mApiService.downloadFile(url))
                    .subscribe(getObserver(dataCallBack));
    }
    /**
     * 参数是json串的post请求
     * @param url
     * @param requestBody
     * @param dataCallBack
     * */
     public <T> void requestFormData(String url, RequestBody requestBody,IDataCallBack dataCallBack){
         if (requestBody!=null){
             RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url,requestBody))
                     .subscribe(getObserver(dataCallBack));
         }else {
             requestFormData(url,requestBody,dataCallBack);
         }
     }






    /**
     * //单个文件+字符串上传
     * uploadStrFile
     * @param url
     * @param requestBody
     * @param part
     * @param dataCallBack
     * */
     public <T> void uploadStrFile(String url,RequestBody requestBody,MultipartBody.Part part,IDataCallBack dataCallBack){
         if (requestBody==null||part==null){
                uploadStrFile(url,requestBody,part,dataCallBack);
         }else {
             RxSchedulersOperator.retryWhenOperator(mApiService.uploadStrFile(url,requestBody,part))
                     .subscribe(getObserver(dataCallBack));
         }
     }
    /**
     * //多个文件+键值对 上传1
     * uploadStrFiles
     * */
     public <T> void uploadStrFiles(String url,RequestBody requestBody,Map<String,T> filesMap,IDataCallBack dataCallBack){
         if (requestBody==null||filesMap.size()==0||filesMap==null){
             uploadStrFiles(url,requestBody,filesMap,dataCallBack);
         }else{
             RxSchedulersOperator.retryWhenOperator(mApiService.uploadStrFiles(url,requestBody,filesMap))
                     .subscribe(getObserver(dataCallBack));
         }
     }
    /**
     * //多个文件+键值对 上传2
     * uploadStrFiles
     * @param url
     * @param requestBody
     * @param parts
     * @param dataCallBack
     * */
     public <T> void uploadStrFiles(String url,RequestBody requestBody, MultipartBody.Part parts,IDataCallBack dataCallBack){
         if (requestBody==null||parts==null){
             uploadStrFiles(url,requestBody,parts,dataCallBack);
         }else{
             RxSchedulersOperator.retryWhenOperator(mApiService.uploadStrFiles(url,requestBody,parts))
                     .subscribe(getObserver(dataCallBack));
         }
     }












    /**
     * 抽取出结果回调的方法
     *
     * @param <T>
     * @param dataCallBack
     * @return
     */
    private <T> Observer<T> getObserver(IDataCallBack<T> dataCallBack) {
        return new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (dataCallBack!=null){
                    dataCallBack.onResponseDisposable(d);
                }
            }
            @Override
            public void onNext(T t) {
                if (dataCallBack!=null){
                    dataCallBack.onStateSucess(t);
                }
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
            }

        };
    }

}
