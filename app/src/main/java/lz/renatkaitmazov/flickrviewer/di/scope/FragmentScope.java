package lz.renatkaitmazov.flickrviewer.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * This annotation represents the fragment lifecycle when using with Dagger.
 *
 * @author Renat Kaitmazov
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentScope {
}
