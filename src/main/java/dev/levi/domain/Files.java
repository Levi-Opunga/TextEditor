package dev.levi.domain;

public class Files {
    private int id;
    private String name;
    private  String path;
    private Long time= System.currentTimeMillis();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getTime() {
        return time;
    }



    public void setTime(Long time) {
        this.time = time;
    }

    public Files(int id, String name, String path, Long time) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.time = time;
    }

    public Files(String name, String path) {
        this.id=0;
        this.name = name;
        this.path = path;
        this.time = System.currentTimeMillis();

    }

    public Files(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.time = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Files{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", time=" + time +
                '}';
    }
}
