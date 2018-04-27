package com.bzh.dytt.lint;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import com.bzh.dytt.lint.json.JSONObject;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyIssueRegistry extends IssueRegistry {

    private static final String TAG = "MyIssueRegistry";

    private static void print(String msg) {
        System.out.println(String.format("%s %s", TAG, msg));
    }

    public static JSONObject getIssuesJson() {
        String jsonStr = getIssusesJsonStr();
        if (jsonStr == null || jsonStr.length() == 0) {
            return null;
        }
        return new JSONObject(jsonStr);
    }

    private static String getIssusesJsonStr() {

        File file = new File("issues.json");
        if (!file.exists()) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static MyIssueRegistry sInstace = new MyIssueRegistry();

    private List<Issue> mIssues = new ArrayList<>();

    @NotNull
    @Override
    public List<Issue> getIssues() {

//        if (true) {
//            return Arrays.asList(LogDetector.ISSUE);
//        }
        System.out.println("Printing stack trace:");
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < elements.length; i++) {
            StackTraceElement s = elements[i];
            System.out.println("\tat " + s.getClassName() + "." + s.getMethodName()
                    + "(" + s.getFileName() + ":" + s.getLineNumber() + ")");
        }



        if (mIssues.size() > 0) {
            return mIssues;
        }

        JSONObject issuesJson = getIssuesJson();

        if (issuesJson == null) {
            return Collections.emptyList();
        }

        String projectPath = issuesJson.getString("project_path");
        String packagePath = issuesJson.getString("package_path");

        File fullPath = new File(projectPath, packagePath);
        if (!fullPath.exists() || !fullPath.isDirectory()) {
            print("Path is null or Path is file " + fullPath);
            return Collections.emptyList();
        }

        String[] files = fullPath.list();
        if (files == null || files.length == 0) {
            print("Directory not file " + fullPath);
            return Collections.emptyList();
        }

        List<String> clazzs = new ArrayList<>();
        String packageName = packagePath.replaceAll("/", ".");
        for (String filePath : files) {
            String className = getClassName(filePath);
            String classFullName = String.format("%s.%s", packageName, className);
            clazzs.add(classFullName);
        }

        if (clazzs.size() == 0) {
            print("Class not found");
            return Collections.emptyList();
        }

        DiskClassLoader diskClassLoader = new DiskClassLoader(getClass().getClassLoader(), fullPath.getAbsolutePath());

        for (String clazz : clazzs) {
            try {
                Class<?> loadedClass = diskClassLoader.loadClass(clazz);
                if (loadedClass != null && loadedClass.getSuperclass() != null) {
                    String className = loadedClass.getSuperclass().toString();
                    if (className.equals(Detector.class.toString())) {
                        Field issueField = loadedClass.getField("ISSUE");
                        Issue issue = (Issue) issueField.get(loadedClass);
                        mIssues.add(issue);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Collections.emptyList();
    }

    private String getClassName(String filePath) {
        return filePath.substring(0, filePath.indexOf('.'));
    }

    @Override
    public int getApi() {
        return com.android.tools.lint.detector.api.ApiKt.CURRENT_API;
    }
}
