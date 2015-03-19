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
import com.sonar.sslr.api.AstNode;
import org.sonar.javascript.ast.visitors.TreeVisitor;
import org.sonar.javascript.model.internal.JavaScriptTree;
import org.sonar.plugins.javascript.api.tree.Tree;
import org.sonar.plugins.javascript.api.tree.declaration.ExportClauseTree;
import org.sonar.plugins.javascript.api.tree.declaration.FromClauseTree;
import org.sonar.plugins.javascript.api.tree.declaration.SpecifierListTree;

import java.util.Iterator;

public class ExportClauseTreeImpl extends JavaScriptTree implements ExportClauseTree {

  private final SpecifierListTree exports;
  private final FromClauseTree fromClause;

  public ExportClauseTreeImpl(SpecifierListTreeImpl exports, AstNode eos) {
    super(Kind.EXPORT_CLAUSE);

    this.exports = exports;
    this.fromClause = null;

    addChildren(exports, eos);
  }

  public ExportClauseTreeImpl(SpecifierListTreeImpl exports, FromClauseTreeImpl fromClause, AstNode eos) {
    super(Kind.EXPORT_CLAUSE);

    this.exports = exports;
    this.fromClause = fromClause;

    addChildren(exports, fromClause, eos);
  }

  @Override
  public SpecifierListTree exports() {
    return exports;
  }

  @Override
  public FromClauseTree fromClause() {
    return fromClause;
  }

  @Override
  public Tree eos() {
    throw new UnsupportedOperationException("Not supported yet in the strongly typed AST.");
  }

  @Override
  public Kind getKind() {
    return Kind.EXPORT_CLAUSE;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.<Tree>forArray(exports, fromClause);
  }

  @Override
  public void accept(TreeVisitor visitor) {
    visitor.visitExportClause(this);
  }
}
