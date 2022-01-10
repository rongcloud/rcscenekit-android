package cn.rongcloud.corekit.bean;

import java.io.Serializable;

/**
 * @author gyn
 * @date 2021/12/16
 */
public class RCNode<T> implements Serializable {
    private String name;
    private String type;
    private T value;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
