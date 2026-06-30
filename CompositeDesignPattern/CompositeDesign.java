package CompositeDesignPattern;

import java.util.ArrayList;
import java.util.List;

interface FileSystem{
    void ls(int indent);
    void openAll(int indent);
    int getSize();
    FileSystem cd(String name);
    String getName();
    boolean isFolder();
}


class File implements FileSystem{
    private String name;
    private int size;
    public File(String name, int size){
        this.name = name;
        this.size = size;
    }

    @Override
    public void ls(int indent){
        String indentSpaces = " ".repeat(indent);
        System.out.println(indentSpaces + name);
    }

    @Override
    public void openAll(int indent){
        String indentSpaces = " ".repeat(indent);
        System.out.println(indentSpaces + name);
    }

        @Override
    public int getSize() {
        return size;
    }

    @Override
    public FileSystem cd(String name) {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isFolder() {
        return false;
    }

}

class Folder implements FileSystem{

    private String name;
    private List<FileSystem> children;

    public Folder(String name){
        this.name = name;
        children = new ArrayList<>();
    }

     public void add(FileSystem item) {
        children.add(item);
    }

    @Override
    public void ls(int indent) {
        String indentSpaces = " ".repeat(indent);
        for (FileSystem child : children) {
            if (child.isFolder()) {
                System.out.println(indentSpaces + "+ " + child.getName());
            } else {
                System.out.println(indentSpaces + child.getName());
            }
        }
    }

    @Override
    public void openAll(int indent) {
        String indentSpaces = " ".repeat(indent);
        System.out.println(indentSpaces + "+ " + name);
        for (FileSystem child : children) {
            child.openAll(indent + 4);
        }
    }

     @Override
    public int getSize() {
        int total = 0;
        for (FileSystem child : children) {
            total += child.getSize();
        }
        return total;
    }

    @Override
    public FileSystem cd(String target) {
        for (FileSystem child : children) {
            if (child.isFolder() && child.getName().equals(target)) {
                return child;
            }
        }
        // not found or not a folder
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isFolder() {
        return true;
    }

    
}

public class CompositeDesign {
 public static void main(String[] args) {
    Folder root = new Folder("root");
    root.add(new File("file1.txt", 1));
    root.add(new File("file2.txt", 1));

    Folder docs = new Folder("Docs");
    docs.add(new File("resume.pdf", 4));
    docs.add(new File("notes.txt",1));
    root.add(docs);

    Folder images = new Folder("images");
        images.add(new File("photo.jpg", 2));
        root.add(images);
         root.ls(0);

        docs.ls(0);

        root.openAll(0);

        FileSystem cwd = root.cd("docs");
        if (cwd != null) {
            cwd.ls(0);
        } else {
            System.out.println("\nCould not cd into docs\n");
        }

        System.out.println(root.getSize());
    }
}   

