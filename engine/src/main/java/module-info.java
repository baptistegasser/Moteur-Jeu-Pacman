module fr.univ.engine {
    requires javafx.graphics;

    opens fr.univ.engine.render to javafx.graphics;

    exports fr.univ.engine.core;
    exports fr.univ.engine.core.math;
    exports fr.univ.engine.render;
    exports fr.univ.engine.render.texture;
}