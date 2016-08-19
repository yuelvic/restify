# Restify
Object based REST for Android. It eliminates boilerplate code.

## Features
* POST
* GET
* UPDATE
* DELETE

## Installation

Add this to your root **build.gradle** file

```
allprojects {
	repositories {
		maven { url "https://jitpack.io" }
	}
}
```

And to your project **build.gradle** file

```
dependencies {
    compile 'com.github.yuelvic:Restify:v0.0.2-alpha'
}
```

## Setup

You must initialize Restify once in your project.

```java
// @param Base URL
Restify.initialize("http://api.movielibrary.com/api/v1/");
```

## Usage

Create a RestObject instance or use the builder class.

```java
//Sample GET method

new RestObject.Builder()
	.setEndpoint("movies")
	.addHeader("HEADER-KEY", "VALUE")
	.addConstraint("limit", 10)
	.findAll(new Restify.Call(
		@Override
		onCompleted() {}
		
		@Override
		onError(Throwable e) {}
		
		@Override
		onSuccess(RestResult result) {
			tvName.setText(result.get("movie_name"));
		}
	));
```

To use form encoding
```java
setUrlFormEncoded(true);
```

You can use multiple API base urls
```java
new Restify.Builder()
    .addUrl("movie", "http://api.movielibrary.com/api/v1")
    .addUrl("music", "http://api.musicboom.com/api/v2")
    .create();
    
RestObject restObject = new RestObject.Builder()
    .useBaseUrl("movie")
    .setEndpoint("movie_list.json")
    .addConstraint("show_cast", true)
    .addConstraint("limit", 10)
    .findAll(
        @Override
    	onCompleted() {}
    		
    	@Override
    	onError(Throwable e) {}
    		
    	@Override
    	onSuccess(RestResult result) {}
    );
    
restObject.useBaseUrl("music");
restObject.setEndpoint("music_list.json");
restObject.addHeader("API-KEY", "68fsduofd8s7f6t7sd");
restObject.addConstraint("genre", "pop");
restObject.findAll(
    @Override
    onCompleted() {}
    		
    @Override
    onError(Throwable e) {}
    		
    @Override
    onSuccess(RestResult result) {}
);
```