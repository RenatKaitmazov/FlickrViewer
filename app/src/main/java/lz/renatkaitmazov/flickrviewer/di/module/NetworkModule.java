package lz.renatkaitmazov.flickrviewer.di.module;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lz.renatkaitmazov.data.cache.Cache;
import lz.renatkaitmazov.data.cache.PhotoCache;
import lz.renatkaitmazov.flickrviewer.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Dependencies related to networking are provided here.
 *
 * @author Renat Kaitmazov
 */
@Module
public final class NetworkModule {

  private static final long CONNECT_TIMEOUT = 15L;

  @Singleton
  @Provides
  static Retrofit provideRetrofit(OkHttpClient httpClient) {
    return new Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .client(httpClient)
      .build();
  }

  @Singleton
  @Provides
  static OkHttpClient provideOkHttpClient() {
    final OkHttpClient.Builder builder = new OkHttpClient.Builder()
      .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
    if (BuildConfig.DEBUG) {
      final HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
      logger.setLevel(HttpLoggingInterceptor.Level.BODY);
      builder.addInterceptor(logger);
    }
    return builder.build();
  }

  @Singleton
  @Provides
  static Cache provideRecentPhotoEntityCache() {
    return new PhotoCache();
  }
}
