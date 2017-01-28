package com.mindshine.clevergrid.io;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class PictureLoader implements ResourceLoaderAware {

	private ResourceLoader resourceLoader;

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public InputStream getPicture(String name) throws IOException {
		Resource banner = resourceLoader.getResource(name);
		return banner.getInputStream();
	}
}
