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
package org.sonar.javascript.model.internal.expression;

import com.google.common.collect.Iterators;
import org.sonar.javascript.ast.visitors.TreeVisitor;
import org.sonar.javascript.model.internal.JavaScriptTree;
import org.sonar.javascript.model.internal.lexical.InternalSyntaxToken;
import org.sonar.plugins.javascript.api.tree.Tree;
import org.sonar.plugins.javascript.api.tree.expression.ThisTree;

import java.util.Iterator;

public class ThisTreeImpl extends JavaScriptTree implements ThisTree {

  private final InternalSyntaxToken token;

  public ThisTreeImpl(InternalSyntaxToken token) {
    super(Kind.THIS, token.getToken());
    this.token = token;

    addChild(token);
  }

  @Override
  public Kind getKind() {
    return Kind.THIS;
  }

  @Override
  public String value() {
    return token.text();
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.<Tree>singletonIterator(
      token);
  }

  @Override
  public void accept(TreeVisitor visitor) {
    visitor.visitThisTree(this);
  }
}
