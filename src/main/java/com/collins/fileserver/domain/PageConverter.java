package com.collins.fileserver.domain;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PageConverter implements Converter<String, Page>{

	@Override
	public Page convert(String source) {
		Page page = new Page();
		page.setName(source);
		return page;
	}


}
