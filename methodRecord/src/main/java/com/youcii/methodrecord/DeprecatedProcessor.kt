package com.youcii.methodrecord

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

/**
 * Created by YouCii on 2020/06/04.
 */
class DeprecatedProcessor : AbstractProcessor() {

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
    }

    override fun process(typeElementSet: MutableSet<out TypeElement>, roundEnvironment: RoundEnvironment?): Boolean {
        for (typeElement in typeElementSet) {
            val elements = roundEnvironment?.getElementsAnnotatedWith(typeElement) ?: continue
        }
        return false
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return LinkedHashSet<String>().apply {
            add(Deprecated::class.java.canonicalName)
            add(java.lang.Deprecated::class.java.canonicalName)
        }
    }

}