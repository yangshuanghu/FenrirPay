package com.example.generators;

import com.example.generators.util.FileUtil;

import java.io.File;

import indi.yume.tools.codegenerator.generator.ClazzGenerator;
import indi.yume.tools.codegenerator.template.TemplateEngine;
import indi.yume.tools.codegenerator.template.VarStringEngine;

/**
 * Created by yume on 15/9/26.
 */
public class Schema {
    private ClazzGenerator view;
    private ClazzGenerator presenter;
    private ClazzGenerator component;
    private ClazzGenerator module;
    private String xmlFileContent;

    private String basePath;

    private VarStringEngine varStringEngine;

    public Schema(String basePath, VarStringEngine varStringEngine) throws Exception {
        this.basePath = basePath;

        File baseFile = new File("");
        String BaseUri = baseFile.getAbsoluteFile() + "/daggergenerator/src/main/res";
        TemplateEngine templateEngine = new TemplateEngine(new File(BaseUri + "/config.xml"), varStringEngine);

        view = templateEngine.setTemplateFile(new File(BaseUri + "/fragment.xml"));
        presenter = templateEngine.setTemplateFile(new File(BaseUri + "/presenter.xml"));
        component = templateEngine.setTemplateFile(new File(BaseUri + "/component.xml"));
        module = templateEngine.setTemplateFile(new File(BaseUri + "/module.xml"));
        xmlFileContent = varStringEngine.analysisString(FileUtil.readFile(new File(BaseUri + "/fragment_layoutxml.xml")));

        this.varStringEngine = varStringEngine;
    }

    public void generatorFiles() throws Exception {
        File viewFile = FileUtil.newFile(basePath,
                "java",
                varStringEngine.analysisString("${basePackage}${fragmentPackage}").replace(".", File.separator),
                varStringEngine.analysisString("${name}Fragment.java"));
        File presenterFile = FileUtil.newFile(basePath,
                "java",
                varStringEngine.analysisString("${basePackage}${presenterPackage}").replace(".", File.separator),
                varStringEngine.analysisString("${name}Presenter.java"));
        File componentFile = FileUtil.newFile(basePath,
                "java",
                varStringEngine.analysisString("${basePackage}${componentPackage}").replace(".", File.separator),
                varStringEngine.analysisString("${name}Component.java"));
        File moduleFile = FileUtil.newFile(basePath,
                "java",
                varStringEngine.analysisString("${basePackage}${modulePackage}").replace(".", File.separator),
                varStringEngine.analysisString("${name}Module.java"));
        File xmlFile = FileUtil.newFile(basePath,
                "res",
                "layout",
                varStringEngine.analysisString("${_-name}_fragment.xml"));

        if(viewFile.exists())
            throw new Error("File " + viewFile.getAbsolutePath() + " is exists");
        if(presenterFile.exists())
            throw new Error("File " + presenterFile.getAbsolutePath() + " is exists");
        if(componentFile.exists())
            throw new Error("File " + componentFile.getAbsolutePath() + " is exists");
        if(moduleFile.exists())
            throw new Error("File " + moduleFile.getAbsolutePath() + " is exists");
        if(xmlFile.exists())
            throw new Error("File " + xmlFile.getAbsolutePath() + " is exists");

        FileUtil.writeToFile(view.render(), viewFile);
        System.out.println("Generate file: " + viewFile.getAbsolutePath());
        FileUtil.writeToFile(presenter.render(), presenterFile);
        System.out.println("Generate file: " + presenterFile.getAbsolutePath());
        FileUtil.writeToFile(component.render(), componentFile);
        System.out.println("Generate file: " + componentFile.getAbsolutePath());
        FileUtil.writeToFile(module.render(), moduleFile);
        System.out.println("Generate file: " + moduleFile.getAbsolutePath());

//            FileUtil.writeToFile(activityXmlFileContent, xmlFile);
//            System.out.println("Generate file: " + xmlFile.getAbsolutePath());
//            ActivityGenerator.generatorAndroidManifest(new File(baseMainPath + File.separator + "AndroidManifest.xml"), view.getBaseClazzInfo());
//            System.out.println("Generate AndroidManifest");

        FileUtil.writeToFile(xmlFileContent, xmlFile);
        System.out.println("Generate file: " + xmlFile.getAbsolutePath());
    }
}
