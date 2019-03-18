package org.dengyu.daydayshoping.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ProductTree {
    private String label;
    private Long value;
    private  Long pid;
    private List<ProductTree> children =new ArrayList<>();

    public ProductTree(String label, Long value, Long pid) {
        this.label = label;
        this.value = value;
        this.pid = pid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public List<ProductTree> getChildren() {
        return children;
    }

    public void setChildren(List<ProductTree> children) {
        this.children = children;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }


}
