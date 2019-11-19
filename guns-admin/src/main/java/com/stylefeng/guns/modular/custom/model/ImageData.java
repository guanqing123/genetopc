package com.stylefeng.guns.modular.custom.model;

import java.net.URL;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;

import lombok.Data;

@Data
@ContentRowHeight(80)
@ColumnWidth(20)
public class ImageData {
	
	@ExcelProperty("图片")
	private URL filePath;
}
