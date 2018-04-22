package com.bzh.dytt.lint;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.SourceCodeScanner;
import com.android.tools.lint.detector.api.TextFormat;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UCallExpression;

import java.util.Arrays;
import java.util.List;

public class LogDetector extends Detector implements SourceCodeScanner {

    public static final Issue ISSUE = Issue.create(
            "LogUsage",
            "Avoid director user android.util.Log",
            "Avoid director user android.util.Log，please use Logger.d/e/i/v/wtf()",
            Category.SECURITY, 5, Severity.ERROR,
            new Implementation(LogDetector.class, Scope.JAVA_FILE_SCOPE));

    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("v", "d", "i", "w", "e", "wtf");
    }

    @Override
    public void visitMethod(JavaContext context, UCallExpression node, PsiMethod method) {
        super.visitMethod(context, node, method);
        if (!context.getEvaluator().isMemberInClass(method, "android.util.Log")) {
            return;
        }
        context.report(ISSUE, node, context.getCallLocation(node, true, true), ISSUE.getExplanation(TextFormat.TEXT));
    }

}
