package com.tofpu.toolbar.processor;

import com.google.auto.service.AutoService;
import com.tofpu.toolbar.api.ToolbarAPI;
import com.tofpu.toolbar.api.ToolbarConstants;
import com.tofpu.toolbar.api.listener.GeneratedListener;
import com.tofpu.toolbar.api.listener.ListenerService;
import com.tofpu.toolbar.api.listener.ListenerWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("com.tofpu.toolbar.api.listener.ListenerWrapper")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class ListenerProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            for (TypeElement annotation : annotations) {
                Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

                Map<String, String> options = processingEnv.getOptions();
                String targetPackage = options.getOrDefault("listenerPackage", ToolbarConstants.PACKAGE_NAME);

                for (Element element : annotatedElements) {
                    writeClass(element, targetPackage);

                    TypeMirror listenerType = getListenerType(element);
                    String simpleName = obtainSimpleName(listenerType);
                    processingEnv.getMessager().printMessage(
                            Diagnostic.Kind.NOTE,
                            "Successfully generated listener " + simpleName
                    );
                }
            }
        } catch (Exception e) {
            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    "Unexpected error during annotation processing: " + e.getMessage()
            );
        }
        return true;
    }

    private void writeClass(Element element, String targetPackage) {
        try {
            TypeMirror eventType = getListenerType(element);
            String className = obtainClassName(eventType);
            String fullClassName = String.format("%s.%s", targetPackage, className);

            JavaFileObject fileObject = processingEnv.getFiler().createSourceFile(fullClassName);
            try (PrintWriter out = new PrintWriter(fileObject.openWriter())) {
                out.println("package " + targetPackage + ";");
                out.println();

                out.println("import " + ListenerService.class.getName() + ";");
                out.println("import " + GeneratedListener.class.getName() + ";");
                out.println("import " + ToolbarAPI.class.getName() + ";");
                out.println("import org.bukkit.event.EventHandler;");
                out.println("import org.bukkit.event.Listener;");
                out.println("import " + eventType + ";");
                out.println();

                out.println("@GeneratedListener");
                out.println("public class " + className + " implements Listener {");
                out.println("    private final ListenerService listenerService = ToolbarAPI.getInstance().listenerService();");
                out.println();
                out.println("    @EventHandler");
                out.println("    public void on(" + obtainSimpleName(eventType) + " event) {");
                out.println("        listenerService.callEventIfPresent(event);");
                out.println("    }");
                out.println("}");
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    "Failed to write generated listener class for " + element.getSimpleName() + ": " + e.getMessage(),
                    element
            );
        }
    }

    private static String obtainClassName(TypeMirror type) {
        if (type instanceof DeclaredType) {
            Element typeElement = ((DeclaredType) type).asElement();
            return typeElement.getSimpleName() + "Listener";
        }
        throw new IllegalArgumentException("Expected a class type");
    }

    private static String obtainSimpleName(TypeMirror type) {
        if (type instanceof DeclaredType) {
            Element typeElement = ((DeclaredType) type).asElement();
            return typeElement.getSimpleName().toString();
        }
        return type.toString();
    }

    @NotNull
    private static TypeMirror getListenerType(Element element) {
        ListenerWrapper listenerWrapper = element.getAnnotation(ListenerWrapper.class);
        if (listenerWrapper == null) {
            throw new IllegalArgumentException("Element is not annotated with @ListenerWrapper");
        }
        try {
            //noinspection ResultOfMethodCallIgnored
            listenerWrapper.type();
        } catch (MirroredTypesException e) {
            if (!e.getTypeMirrors().isEmpty()) {
                return e.getTypeMirrors().get(0);
            }
        }
        throw new RuntimeException("Failed to obtain ListenerWrapper#type() value");
    }
}