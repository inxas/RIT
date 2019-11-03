package net.inxas.rit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import net.inxas.rit.property.Property;

/**
 * RITのエントリです。
 * 
 * @author inxas
 * @since  2019/10/27
 */
public class RITEntry {
    private final String projectName;
    private final Path projectRootPath;

    public RITEntry(String projectName, Path projectRootPath) {
        RIT.supportCheck();
        if (projectName == null) {
            throw new NullPointerException("projectName must not be null!");
        }
        if (projectRootPath == null) {
            throw new NullPointerException("projectRootPath must not be null!");
        }

        this.projectName = projectName;
        this.projectRootPath = projectRootPath;
    }

    public String getProjectName() {
        return projectName;
    }

    public Path getProjectRootPath() {
        return projectRootPath;
    }

    /**
     * プロジェクトをビルドします。
     * 
     * @throws RITProjectException 作成しようとした場所が既に存在していた場合にスローされます。
     * @throws IOException         プロジェクトのビルド中に何らかの入出力例外が発生した場合にスローされます。
     */
    public void build() throws RITProjectException, IOException {
        final Path projectPath = Paths.get(projectRootPath.toString(), projectName);
        if (Files.exists(projectPath)) {
            throw new RITProjectException(projectName + " already exists");
        }
        Path metaFile = Paths.get(projectPath.toString(), ".meta", projectName + ".ritmeta");
        Property metaProperty = new Property(metaFile);
        String[] metaTable = {
                "RIT.project", "Project",
                "RIT.project.resource", "Resource",
                "RIT.project.resource.layout", "Layout",
                "Project.name", projectName,
                "Project.root", ".." + File.separator,
                "Project.manifest", "%Project.root%" + projectName + ".manifest",
                "Project.meta", "%Project.root%" + ".meta" + File.separator,
                "Project.resource", "%Project.root%" + "resource" + File.separator,
                "Resource.layout", "%Project.resource%" + "layout" + File.separator,
                "Layout.main", "%Resource.layout%" + projectName + ".rit"
        };
        metaProperty.putAll(metaTable);

        LinkedHashMap<String, String> map = metaProperty.getExpandedMap();
        String metaPath = metaProperty.getPath().getParent().toString();
        createFile(Paths.get(metaPath, map.get("Project.manifest")));
        createFile(Paths.get(metaPath, map.get("Layout.main")));
        createFile(metaFile);

        metaProperty.save();
    }

    @SuppressWarnings("static-method")
    private void createFile(Path path) throws IOException {
        Path parent = path.getParent();
        if (Files.notExists(parent)) {
            Files.createDirectories(parent);
        }
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
    }
}
