# Restify
Object based REST for Android

## Installation

Add this to your **build.gradle** file

```
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
```
  	dependencies {
		compile 'com.github.yuelvic:restify-android:v0.0.1-alpha'
	}
```

## Setup

You must initialize Restify once in your project.

```java
Restify.initialize("http://api.movielibrary.com/api/v1/");
```

## Usage

Create a RestObject instance or use the builder class.

```java
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
