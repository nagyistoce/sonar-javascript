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
package org.sonar.javascript.model.internal.declaration;

import com.google.common.collect.Iterators;
import org.sonar.javascript.ast.visitors.TreeVisitor;
import org.sonar.javascript.model.internal.JavaScriptTree;
import org.sonar.javascript.model.internal.expression.IdentifierTreeImpl;
import org.sonar.javascript.model.internal.lexical.InternalSyntaxToken;
import org.sonar.plugins.javascript.api.tree.Tree;
import org.sonar.plugins.javascript.api.tree.declaration.SpecifierTree;
import org.sonar.plugins.javascript.api.tree.expression.IdentifierTree;
import org.sonar.plugins.javascript.api.tree.lexical.SyntaxToken;

import javax.annotation.Nullable;
import java.util.Iterator;

public class SpecifierTreeImpl extends JavaScriptTree implements SpecifierTree {

  private final Kind kind;
  private IdentifierTree name;
  private final SyntaxToken asToken;
  private final IdentifierTree localName;

  public SpecifierTreeImpl(Kind kind, IdentifierTreeImpl name) {
    super(kind);
    this.kind = kind;
    this.name = name;
    this.asToken = null;
    this.localName = null;

    addChild(name);
  }

  public SpecifierTreeImpl(Kind kind, InternalSyntaxToken asToken, IdentifierTreeImpl localName) {
    super(kind);
    this.kind = kind;
    this.asToken = asToken;
    this.localName = localName;

    addChildren(asToken, localName);
  }

  public SpecifierTreeImpl complete(IdentifierTreeImpl name) {
    this.name = name;

    prependChildren(name);
    return this;
  }

  @Override
  public IdentifierTree name() {
    return name;
  }

  @Nullable
  @Override
  public SyntaxToken asToken() {
    return asToken;
  }

  @Nullable
  @Override
  public IdentifierTree localName() {
    return localName;
  }

  @Override
  public Kind getKind() {
    return kind;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.<Tree>forArray(name, localName);
  }

  @Override
  public void accept(TreeVisitor visitor) {
    visitor.visitSpecifier(this);
  }
}
