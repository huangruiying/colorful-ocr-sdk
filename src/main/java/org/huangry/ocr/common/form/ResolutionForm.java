package org.huangry.ocr.common.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class ResolutionForm implements Serializable{

    @JsonProperty("file")
    private MultipartFile file;

    private String contentType;

    private String name;

    private String type;

    /** 语言 */
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
        if(file != null){
            this.contentType = file.getContentType();
            this.name = file.getOriginalFilename();
            if(this.name != null && this.name.contains(".")){
                this.type = this.name.substring(this.name.indexOf("."));
            }
        }
    }

    public String getContentType() {
        return contentType;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
