/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011 SonarSource and Eriks Nukis
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.javascript.model.declaration;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.sonar.javascript.model.JavaScriptTreeModelTest;
import org.sonar.plugins.javascript.api.tree.Tree.Kind;
import org.sonar.plugins.javascript.api.tree.declaration.DefaultExportDeclarationTree;

public class DefaultExportDeclarationTreeModelTest extends JavaScriptTreeModelTest {


  @Test
  public void expression() throws Exception {
    DefaultExportDeclarationTree tree = parse("export default a ;", Kind.DEFAULT_EXPORT_DECLARATION);

    assertThat(tree.is(Kind.DEFAULT_EXPORT_DECLARATION)).isTrue();
    assertThat(tree.exportToken().text()).isEqualTo("export");
    assertThat(tree.defaultToken().text()).isEqualTo("default");
    assertThat(expressionToString(tree.object())).isEqualTo("a ;");
    // TODO: add eos
  }

  @Test
  public void generator() throws Exception {
    DefaultExportDeclarationTree tree = parse("export default function * f ( ) { }", Kind.DEFAULT_EXPORT_DECLARATION);

    assertThat(tree.is(Kind.DEFAULT_EXPORT_DECLARATION)).isTrue();
    assertThat(tree.exportToken().text()).isEqualTo("export");
    assertThat(tree.defaultToken().text()).isEqualTo("default");
    assertThat(expressionToString(tree.object())).isEqualTo("function * f ( ) { }");
    // TODO: add eos
  }

  @Test
  public void function() throws Exception {
    DefaultExportDeclarationTree tree = parse("export default function f ( ) { }", Kind.DEFAULT_EXPORT_DECLARATION);

    assertThat(tree.is(Kind.DEFAULT_EXPORT_DECLARATION)).isTrue();
    assertThat(tree.exportToken().text()).isEqualTo("export");
    assertThat(tree.defaultToken().text()).isEqualTo("default");
    assertThat(expressionToString(tree.object())).isEqualTo("function f ( ) { }");
    // TODO: add eos
  }

}
