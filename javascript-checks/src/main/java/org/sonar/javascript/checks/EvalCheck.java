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
import org.sonar.javascript.model.interfaces.Tree.Kind;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.sonar.sslr.api.AstNode;

@Rule(
  key = "Eval",
  name = "Code should not be dynamically injected and executed to prevent Eval Injection vulnerability",
  priority = Priority.CRITICAL,
  tags = {Tags.CWE, Tags.OWASP_A3, Tags.SECURITY})
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.INPUT_VALIDATION_AND_REPRESENTATION)
@SqaleConstantRemediation("30min")
public class EvalCheck extends SquidCheck<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(Kind.CALL_EXPRESSION);
  }

  @Override
  public void visitNode(AstNode node) {
    AstNode simpleCallExprNode = node.getFirstChild();

    AstNode memberExpressionNode = simpleCallExprNode.getFirstChild();

    if (memberExpressionNode.isNot(Kind.SUPER) && "eval".equals(memberExpressionNode.getTokenValue())) {
      getContext().createLineViolation(this, "Remove this use of the \"eval\" function.", node);
    }
  }

}
