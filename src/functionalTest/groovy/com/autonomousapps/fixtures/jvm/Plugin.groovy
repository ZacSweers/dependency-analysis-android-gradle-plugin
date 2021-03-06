package com.autonomousapps.fixtures.jvm

final class Plugin {

  static final KOTLIN_VERSION = '1.3.72'

  final String id, version
  final boolean apply

  Plugin(String id, String version = null, boolean apply = true) {
    this.id = id
    this.version = version
    this.apply = apply
  }

  static Plugin dependencyAnalysisPlugin() {
    return new Plugin(
      'com.autonomousapps.dependency-analysis',
      System.getProperty("com.autonomousapps.pluginversion")
    )
  }

  static Plugin kotlinPlugin(boolean apply = true, String version = KOTLIN_VERSION) {
    return plugin('org.jetbrains.kotlin.jvm', version, apply)
  }

  static Plugin javaLibraryPlugin() {
    return plugin('java-library')
  }

  static Plugin applicationPlugin() {
    // this also applies `java`
    return plugin('application')
  }

  private static Plugin plugin(String id, String version = null, boolean apply = true) {
    return new Plugin(id, version, apply)
  }

  @Override
  String toString() {
    String s = "id '$id'"
    if (version) {
      s += " version '$version'"
    }
    if (!apply) {
      s += ' apply false'
    }
    return s
  }
}
