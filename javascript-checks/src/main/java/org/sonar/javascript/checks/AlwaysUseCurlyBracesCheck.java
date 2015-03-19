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
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.javascript.api.tree.Tree.Kind;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.sonar.sslr.api.AstNode;

@Rule(
  key = "CurlyBraces",
  name = "Always use curly braces for \"if/else/for/while/do\" statements",
  priority = Priority.MAJOR,
  tags = {Tags.CONVENTION, Tags.CWE, Tags.MISRA})
@BelongsToProfile(title = CheckList.SONAR_WAY_PROFILE, priority = Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("10min")
public class AlwaysUseCurlyBracesCheck extends SquidCheck<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(
      Kind.IF_STATEMENT,
      Kind.FOR_IN_STATEMENT,
      Kind.FOR_STATEMENT,
      Kind.WHILE_STATEMENT,
      Kind.DO_WHILE_STATEMENT,
      Kind.ELSE_CLAUSE);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (!isElseIf(astNode) && !astNode.hasDirectChildren(Kind.BLOCK)) {
      getContext().createLineViolation(this, "Missing curly brace.", astNode);
    }
  }

  private boolean isElseIf(AstNode statement) {
    return statement.is(Kind.ELSE_CLAUSE) && statement.hasDirectChildren(Kind.IF_STATEMENT);
  }

}
