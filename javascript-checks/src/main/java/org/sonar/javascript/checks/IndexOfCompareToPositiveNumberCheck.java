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

import java.util.List;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.javascript.checks.utils.SubscriptionBaseVisitor;
import org.sonar.plugins.javascript.api.tree.Tree;
import org.sonar.plugins.javascript.api.tree.Tree.Kind;
import org.sonar.plugins.javascript.api.tree.expression.BinaryExpressionTree;
import org.sonar.plugins.javascript.api.tree.expression.CallExpressionTree;
import org.sonar.plugins.javascript.api.tree.expression.DotMemberExpressionTree;
import org.sonar.plugins.javascript.api.tree.expression.ExpressionTree;
import org.sonar.plugins.javascript.api.tree.expression.IdentifierTree;
import org.sonar.plugins.javascript.api.tree.expression.LiteralTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.google.common.collect.ImmutableList;

@Rule(
  key = "S2692",
  name = "\"indexOf\" checks should not be for positive numbers",
  priority = Priority.MAJOR,
  tags = {Tags.BUG})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.LOGIC_RELIABILITY)
@SqaleConstantRemediation("2min")
public class IndexOfCompareToPositiveNumberCheck extends SubscriptionBaseVisitor {

  @Override
  public List<Kind> nodesToVisit() {
    return ImmutableList.of(Kind.GREATER_THAN);
  }

  @Override
  public void visitNode(Tree tree) {
    BinaryExpressionTree expression = (BinaryExpressionTree) tree;

    if (isZero(expression.rightOperand()) && isIndexOfCall(expression.leftOperand())) {
      addIssue(tree, "0 is a valid index, but is ignored by this check.");
    }

  }

  private static boolean isIndexOfCall(ExpressionTree expression) {
    if (expression.is(Kind.CALL_EXPRESSION)) {
      CallExpressionTree callExpr = (CallExpressionTree) expression;

      if (callExpr.arguments().parameters().size() == 1 && callExpr.callee().is(Kind.DOT_MEMBER_EXPRESSION)) {
        DotMemberExpressionTree memberExpr = (DotMemberExpressionTree) ((CallExpressionTree) expression).callee();
        return isExpressionIdentifierNamed(memberExpr.property(), "indexOf");
      }
    }
    return false;
  }

  private static boolean isExpressionIdentifierNamed(ExpressionTree tree, String name) {
    return tree instanceof IdentifierTree && name.equals(((IdentifierTree) tree).name());
  }

  private static boolean isZero(ExpressionTree expression) {
    return expression.is(Kind.NUMERIC_LITERAL) && "0".equals(((LiteralTree) expression).value());
  }

}
