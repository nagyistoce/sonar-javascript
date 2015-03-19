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
package org.sonar.javascript.checks;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.javascript.model.internal.statement.IfStatementTreeImpl;
import org.sonar.plugins.javascript.api.tree.Tree.Kind;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.sonar.sslr.api.AstNode;

@Rule(
  key = "ElseIfWithoutElse",
  name = "\"if ... else if\" constructs shall be terminated with an \"else\" clause",
  priority = Priority.MAJOR,
  tags = {Tags.CERT, Tags.MISRA})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.LOGIC_RELIABILITY)
@SqaleConstantRemediation("20min")
public class ElseIfWithoutElseCheck extends SquidCheck<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(Kind.IF_STATEMENT);
  }

  @Override
  public void visitNode(AstNode node) {
    IfStatementTreeImpl ifStmt = (IfStatementTreeImpl) node;

    if (isElseIf(ifStmt) && !ifStmt.hasElse()) {
      getContext().createLineViolation(this, "Add the missing \"else\" clause.", node);
    }
  }

  private boolean isElseIf(AstNode node) {
    return node.getParent().is(Kind.ELSE_CLAUSE);
  }

}
