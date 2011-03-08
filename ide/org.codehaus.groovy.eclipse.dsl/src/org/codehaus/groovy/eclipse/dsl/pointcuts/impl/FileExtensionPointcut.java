/*******************************************************************************
 * Copyright (c) 2011 Codehaus.org, SpringSource, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Andrew Eisenberg - Initial implemenation
 *******************************************************************************/
package org.codehaus.groovy.eclipse.dsl.pointcuts.impl;

import org.codehaus.groovy.eclipse.dsl.pointcuts.AbstractPointcut;
import org.codehaus.groovy.eclipse.dsl.pointcuts.BindingSet;
import org.codehaus.groovy.eclipse.dsl.pointcuts.GroovyDSLDContext;

/**
 * Tests that the file currently being checked ends in the given extension.
 * Do not include the dot in the pointcut, but the dot must exist before the extension
 * This pointcut should be optimized so that it runs before any others in an 'and' or 'or' clause.  Also, its result should be cached in the 
 * pattern providing a fail/succeed fast strategy.
 * @author andrew
 * @created Feb 10, 2011
 */
public class FileExtensionPointcut extends AbstractPointcut {

    public FileExtensionPointcut(String containerIdentifier) {
        super(containerIdentifier);
    }

    @Override
    public BindingSet matches(GroovyDSLDContext pattern) {
        if (pattern.fileName != null && pattern.fileName.endsWith("." + (String) getFirstArgument())) {
            return new BindingSet().addDefaultBinding(pattern.fileName);
        } else {
            return null;
        }
    }
    
    @Override
    public boolean fastMatch(GroovyDSLDContext pattern) {
        return matches(pattern) != null;
    }

    @Override
    public String verify() {
        String maybeStatus = allArgsAreStrings();
        if (maybeStatus != null) {
            return maybeStatus;
        }
        maybeStatus = hasOneArg();
        if (maybeStatus != null) {
            return maybeStatus;
        }
        return super.verify();
    }
}