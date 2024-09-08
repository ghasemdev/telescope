package app.cash.redwood.buildsettings;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.initialization.Settings;

@SuppressWarnings("unused") // Invoked reflectively by Gradle.
public class RedwoodSettingsPlugin implements Plugin<Settings> {
    @Override
    public void apply(Settings target) {
        Action<Project> applyRedwoodPlugin = project -> project.getPlugins().apply("app.cash.redwood.build");

        target
            .getGradle()
            .allprojects(
                project -> {
                    if (project.getPath().equals(":")) {
                        // The root project needs to evaluate the buildscript classpath before applying.
                        // Once we move to the plugins DSL in the main build we can remove this conditional.
                        project.afterEvaluate(applyRedwoodPlugin);
                    } else {
                        project.beforeEvaluate(applyRedwoodPlugin);
                    }
                }
            );
    }
}
