package io.github.tofpu.processor;

import com.google.auto.service.AutoService;
import io.github.tofpu.ListenerWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("io.github.tofpu.ListenerWrapper")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class ListenerProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            for (TypeElement annotation : annotations) {
                Set<? extends Element> annotatedElements
                        = roundEnv.getElementsAnnotatedWith(annotation);
                System.out.println("Found annotated elements: " + annotatedElements);

                annotatedElements.forEach(this::writeClass);
            }

            System.out.println("processor");
            processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, "processor");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void writeClass(Element element) {
        try {
            TypeMirror eventType = getListenerType(element);
            String className = obtainClassName(eventType);

            JavaFileObject fileObject = processingEnv.getFiler()
                    .createSourceFile(className);
            try (PrintWriter out = new PrintWriter(fileObject.openWriter())) {
                out.println("package io.tofpu.toolbar;");
                out.println();

                out.println("import io.tofpu.toolbar.listener.ListenerService;");
                out.println();

                out.println("@io.github.tofpu.GeneratedListener");
                out.println(String.format("public class %s implements org.bukkit.event.Listener {", className));
                out.println("   private final ListenerService listenerService = ToolbarAPI.getInstance().listenerService();");
                out.println();

                out.println("   @org.bukkit.event.EventHandler");
                out.println(String.format("   void on(%s event) {", eventType));
                out.println("       listenerService.callEventIfPresent(event);");
                out.println("   }");
                out.println();

                out.println("}");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String obtainClassName(TypeMirror eventType) {
        String[] split = eventType.toString().split("\\.");
        String className = String.format("%sListener", split[split.length-1]);
        return className;
    }

    @NotNull
    private static TypeMirror getListenerType(Element element) {
        ListenerWrapper listenerWrapper = element.getAnnotation(ListenerWrapper.class);
        try {
            //noinspection ResultOfMethodCallIgnored
            listenerWrapper.type();
        } catch (MirroredTypesException e) {
            return e.getTypeMirrors().get(0);
        }
        throw new RuntimeException("Failed to obtain ListenerWrapper#type() value");
    }
}
