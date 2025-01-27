/*
 * Copyright (c) 2019 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at:
 *
 *     https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.jkube.maven.plugin.mojo.build;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.IOException;

import static org.eclipse.jkube.kit.resource.helm.HelmServiceUtil.initHelmConfig;

@Mojo(name = "helm-lint", defaultPhase = LifecyclePhase.INTEGRATION_TEST, requiresDependencyResolution = ResolutionScope.COMPILE)
public class HelmLintMojo extends HelmMojo {

  @Override
  public void executeInternal() throws MojoExecutionException {
    try {
      helm = initHelmConfig(getDefaultHelmType(), javaProject, getKubernetesTemplate(), helm)
        .build();
      jkubeServiceHub.getHelmService().lint(helm);
    } catch (IOException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }

  }
}
