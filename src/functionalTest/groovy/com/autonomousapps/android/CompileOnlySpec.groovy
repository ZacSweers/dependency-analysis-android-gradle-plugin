package com.autonomousapps.android

import com.autonomousapps.fixtures.AnotherCompileOnlyProject
import com.autonomousapps.fixtures.CompileOnlyTestProject
import spock.lang.Unroll

import static com.autonomousapps.utils.Runner.build
import static com.google.common.truth.Truth.assertThat

@SuppressWarnings("GroovyAssignabilityCheck")
final class CompileOnlySpec extends AbstractAndroidSpec {

  @Unroll
  def "compileOnly deps are never suggested to be changed (#gradleVersion AGP #agpVersion)"() {
    def project = new AnotherCompileOnlyProject(agpVersion)
    androidProject = project.newProject()

    when:
    build(gradleVersion, androidProject, 'buildHealth')

    then:
    def actualAdvice = androidProject.adviceFor(project.androidKotlinLib)
    def expectedAdvice = project.expectedAdviceForLib
    assertThat(expectedAdvice).containsExactlyElementsIn(actualAdvice)

    where:
    [gradleVersion, agpVersion] << gradleAgpMatrix()
  }

  @Unroll
  def "reports dependencies that could be compileOnly (#gradleVersion AGP #agpVersion)"() {
    given:
    def project = new CompileOnlyTestProject(agpVersion)
    androidProject = project.newProject()

    when:
    build(gradleVersion, androidProject, 'buildHealth')

    then:
    def actualAdviceForApp = androidProject.adviceFor(project.appSpec)
    def expectedAdviceForApp = project.expectedAdviceForApp
    assertThat(expectedAdviceForApp).containsExactlyElementsIn(actualAdviceForApp)

    and:
    def actualAdviceForAndroidKotlinLib = androidProject.adviceFor(project.androidKotlinLib)
    def expectedAdviceForAndroidKotlinLib = project.expectedAdviceForAndroidKotlinLib
    assertThat(expectedAdviceForAndroidKotlinLib).containsExactlyElementsIn(actualAdviceForAndroidKotlinLib)

    and:
    def actualAdviceForJavaJvmLib = androidProject.adviceFor(project.javaJvmLib)
    def expectedAdviceForJavaJvmLib = project.expectedAdviceForJavaJvmLib
    assertThat(expectedAdviceForJavaJvmLib).containsExactlyElementsIn(actualAdviceForJavaJvmLib)

    where:
    [gradleVersion, agpVersion] << gradleAgpMatrix()
  }
}
