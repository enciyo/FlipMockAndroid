<p align="center">
  <img src="https://fbflipper.com/img/icon.png" alt="logo" width="20%"/>
</p>
<h1 align="center">
  FlipMock
</h1>

<p align="center">
  <a href="">FlipMock</a> is a extended <a href="https://github.com/facebook/flipper"> Flipper </a> plugin that manipulates network response.
</p>


## Table of Contents

- [Using FlipMock](#using-flipmock)
  - [Application](#application)
  - [OkHttp](#okhttp)
- [How can I mock the response?](#how-can-i-mock-the-response)


## SDK Features

- [x] Support <a href="https://github.com/square/okhttp">OkHttp</a>
### Waiting for FlipMock Desktop Plugin support
- [x] Mock the response by HTTP method. 
- [x] Mock the response by query parameters. 
- [x] For response, support HTTP code



## Installation


```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation ''
}
```

# Using FlipMock

## Application 


```kotlin

class FlipperApp : Application() {

    companion object {
        val flipMockPlugin = FlipMockPlugin.getInstance()
    }

    override fun onCreate() {
        super.onCreate()
        
        val client = AndroidFlipperClient.getInstance(this)
        ///....    
        client.addPlugin(flipMockPlugin)
        ///...
        client.start()
    }

}
```

## OkHttp
```kotlin
   fun provideOkHttpClient(): OkHttpClient {
        val okhttp = OkHttpClient.Builder()
        if (BuildConfig.DEBUG){
            okhttp.addInterceptor(FlipperApp.flipMockPlugin.interceptor)
        }
        return okhttp.build()
    }

```


# How can I mock the response?
In this section, you can mock response using   <a href="">FlipMock</a>  desktop Plugin on the  <a href="https://github.com/facebook/flipper"> Flipper.</a>

<a href="">How to use FlipMock Desktop Plugin?</a>


