package com.example.demo;

public class Mensaje {
    public int id;
    public String msg;

    public Mensaje() {

    }

    public Mensaje(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mensaje mensaje = (Mensaje) o;

        if (id != mensaje.id) return false;
        return msg != null ? msg.equals(mensaje.msg) : mensaje.msg == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        return result;
    }
}
