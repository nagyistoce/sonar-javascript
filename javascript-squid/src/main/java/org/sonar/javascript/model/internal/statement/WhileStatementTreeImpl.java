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
package org.sonar.javascript.model.internal.statement;

import com.google.common.collect.Iterators;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import org.sonar.javascript.ast.visitors.TreeVisitor;
import org.sonar.javascript.model.internal.JavaScriptTree;
import org.sonar.javascript.model.internal.lexical.InternalSyntaxToken;
import org.sonar.plugins.javascript.api.tree.Tree;
import org.sonar.plugins.javascript.api.tree.expression.ExpressionTree;
import org.sonar.plugins.javascript.api.tree.lexical.SyntaxToken;
import org.sonar.plugins.javascript.api.tree.statement.StatementTree;
import org.sonar.plugins.javascript.api.tree.statement.WhileStatementTree;

import java.util.Iterator;

public class WhileStatementTreeImpl extends JavaScriptTree implements WhileStatementTree {

  private final SyntaxToken whileKeyword;
  private final SyntaxToken openingParenthesis;
  private final ExpressionTree condition;
  private final SyntaxToken closingParenthesis;
  private final StatementTree statement;

  public WhileStatementTreeImpl(InternalSyntaxToken whileKeyword, InternalSyntaxToken openingParenthesis, ExpressionTree condition, InternalSyntaxToken closingParenthesis,
    StatementTree statement) {
    super(Kind.WHILE_STATEMENT);
    this.whileKeyword = whileKeyword;
    this.openingParenthesis = openingParenthesis;
    this.condition = condition;
    this.closingParenthesis = closingParenthesis;
    this.statement = statement;

    addChildren(whileKeyword, openingParenthesis, (AstNode) condition, closingParenthesis, (AstNode) statement);
  }

  @Override
  public SyntaxToken whileKeyword() {
    return whileKeyword;
  }

  @Override
  public SyntaxToken openParenthesis() {
    return openingParenthesis;
  }

  @Override
  public ExpressionTree condition() {
    return condition;
  }

  @Override
  public SyntaxToken closeParenthesis() {
    return closingParenthesis;
  }

  @Override
  public StatementTree statement() {
    return statement;
  }

  @Override
  public AstNodeType getKind() {
    return Kind.WHILE_STATEMENT;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(condition, statement);
  }

  @Override
  public void accept(TreeVisitor visitor) {
    visitor.visitWhileStatement(this);
  }
}
