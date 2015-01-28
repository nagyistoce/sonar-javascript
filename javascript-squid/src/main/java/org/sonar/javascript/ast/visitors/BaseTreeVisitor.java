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
package org.sonar.javascript.ast.visitors;

import org.sonar.javascript.model.implementations.SeparatedList;
import org.sonar.javascript.model.interfaces.ModuleTree;
import org.sonar.javascript.model.interfaces.Tree;
import org.sonar.javascript.model.interfaces.declaration.AccessorMethodDeclarationTree;
import org.sonar.javascript.model.interfaces.declaration.ArrayBindingPatternTree;
import org.sonar.javascript.model.interfaces.declaration.BindingPropertyTree;
import org.sonar.javascript.model.interfaces.declaration.DefaultExportDeclarationTree;
import org.sonar.javascript.model.interfaces.declaration.FromClauseTree;
import org.sonar.javascript.model.interfaces.declaration.FunctionDeclarationTree;
import org.sonar.javascript.model.interfaces.declaration.GeneratorMethodDeclarationTree;
import org.sonar.javascript.model.interfaces.declaration.ImportClauseTree;
import org.sonar.javascript.model.interfaces.declaration.ImportDeclarationTree;
import org.sonar.javascript.model.interfaces.declaration.ImportModuleDeclarationTree;
import org.sonar.javascript.model.interfaces.declaration.InitializedBindingElementTree;
import org.sonar.javascript.model.interfaces.declaration.MethodDeclarationTree;
import org.sonar.javascript.model.interfaces.declaration.NameSpaceExportDeclarationTree;
import org.sonar.javascript.model.interfaces.declaration.NamedExportDeclarationTree;
import org.sonar.javascript.model.interfaces.declaration.ParameterListTree;
import org.sonar.javascript.model.interfaces.declaration.ScriptTree;
import org.sonar.javascript.model.interfaces.declaration.SpecifierListTree;
import org.sonar.javascript.model.interfaces.declaration.SpecifierTree;
import org.sonar.javascript.model.interfaces.expression.ArrayLiteralTree;
import org.sonar.javascript.model.interfaces.expression.ArrowFunctionTree;
import org.sonar.javascript.model.interfaces.expression.AssignmentExpressionTree;
import org.sonar.javascript.model.interfaces.expression.BinaryExpressionTree;
import org.sonar.javascript.model.interfaces.expression.BracketMemberExpressionTree;
import org.sonar.javascript.model.interfaces.expression.CallExpressionTree;
import org.sonar.javascript.model.interfaces.expression.ClassTree;
import org.sonar.javascript.model.interfaces.expression.ComputedPropertyNameTree;
import org.sonar.javascript.model.interfaces.expression.ConditionalExpressionTree;
import org.sonar.javascript.model.interfaces.expression.DotMemberExpressionTree;
import org.sonar.javascript.model.interfaces.expression.FunctionExpressionTree;
import org.sonar.javascript.model.interfaces.expression.IdentifierTree;
import org.sonar.javascript.model.interfaces.expression.LiteralTree;
import org.sonar.javascript.model.interfaces.expression.NewExpressionTree;
import org.sonar.javascript.model.interfaces.expression.ObjectLiteralTree;
import org.sonar.javascript.model.interfaces.expression.PairPropertyTree;
import org.sonar.javascript.model.interfaces.expression.ParenthesisedExpressionTree;
import org.sonar.javascript.model.interfaces.expression.TaggedTemplateTree;
import org.sonar.javascript.model.interfaces.expression.TemplateExpressionTree;
import org.sonar.javascript.model.interfaces.expression.TemplateLiteralTree;
import org.sonar.javascript.model.interfaces.expression.ThisTree;
import org.sonar.javascript.model.interfaces.expression.UnaryExpressionTree;
import org.sonar.javascript.model.interfaces.expression.YieldExpressionTree;
import org.sonar.javascript.model.interfaces.statement.BlockTree;
import org.sonar.javascript.model.interfaces.statement.BreakStatementTree;
import org.sonar.javascript.model.interfaces.statement.ContinueStatementTree;
import org.sonar.javascript.model.interfaces.statement.DebuggerStatementTree;
import org.sonar.javascript.model.interfaces.statement.DoWhileStatementTree;
import org.sonar.javascript.model.interfaces.statement.ElseClauseTree;
import org.sonar.javascript.model.interfaces.statement.EmptyStatementTree;
import org.sonar.javascript.model.interfaces.statement.ExpressionStatementTree;
import org.sonar.javascript.model.interfaces.statement.ForInStatementTree;
import org.sonar.javascript.model.interfaces.statement.ForOfStatementTree;
import org.sonar.javascript.model.interfaces.statement.ForStatementTree;
import org.sonar.javascript.model.interfaces.statement.IfStatementTree;
import org.sonar.javascript.model.interfaces.statement.LabelledStatementTree;
import org.sonar.javascript.model.interfaces.statement.ReturnStatementTree;
import org.sonar.javascript.model.interfaces.statement.SwitchStatementTree;
import org.sonar.javascript.model.interfaces.statement.ThrowStatementTree;
import org.sonar.javascript.model.interfaces.statement.TryStatementTree;
import org.sonar.javascript.model.interfaces.statement.VariableDeclarationTree;
import org.sonar.javascript.model.interfaces.statement.VariableStatementTree;
import org.sonar.javascript.model.interfaces.statement.WhileStatementTree;
import org.sonar.javascript.model.interfaces.statement.WithStatementTree;

import javax.annotation.Nullable;
import java.util.List;

public class BaseTreeVisitor implements TreeVisitor {

  protected void scan(List<? extends Tree> trees) {
    for (Tree tree : trees) {
      scan(tree);
    }
  }

  protected void scan(@Nullable Tree tree) {
    if (tree != null) {
      tree.accept(this);
    }
  }

  protected void scan(SeparatedList<? extends Tree> trees) {
    for (Tree tree : trees) {
      scan(tree);
    }
  }

  public void visitScript(ScriptTree tree) {
    scan(tree.items());
  }

  public void visitModule(ModuleTree tree) {
    scan(tree.items());
  }

  public void visitImportDeclaration(ImportDeclarationTree tree) {
    scan(tree.importClause());
    scan(tree.fromClause());
  }

  public void visitImportModuletDeclaration(ImportModuleDeclarationTree tree) {
    scan(tree.moduleName());
  }

  public void visitImportClause(ImportClauseTree tree) {
    scan(tree.namedImport());
  }

  public void visitSpecifierList(SpecifierListTree tree) {
    scan(tree.specifiers());
  }

  public void visitSpecifier(SpecifierTree tree) {
    scan(tree.name());
    scan(tree.localName());
  }

  public void visitFromClause(FromClauseTree tree) {
    scan(tree.module());
  }

  public void visitDefaultExportDeclaration(DefaultExportDeclarationTree tree) {
    scan(tree.object());
  }

  public void visitNameSpaceExportDeclaration(NameSpaceExportDeclarationTree tree) {
    scan(tree.fromClause());
  }

  public void visitNamedExportDeclaration(NamedExportDeclarationTree tree) {
    scan(tree.object());
  }

  public void visitvariableStatement(VariableStatementTree tree) {
    scan(tree.declaration());
  }

  public void visitVariableDeclaration(VariableDeclarationTree tree) {
    scan(tree.variables());
  }

  public void visitClassDeclaration(ClassTree tree) {
    scan(tree.name());
    scan(tree.superClass());
    scan(tree.elements());
  }

  public void visitMethodDeclaration(MethodDeclarationTree tree) {
    scan(tree.name());
    scan(tree.parameters());
    scan(tree.body());
  }

  public void visitParameterList(ParameterListTree tree) {
    scan(tree.parameters());
  }

  public void visitFunctionDeclaration(FunctionDeclarationTree tree) {
    scan(tree.name());
    scan(tree.parameters());
    scan(tree.body());
  }

  public void visitBlock(BlockTree tree) {
    scan(tree.statements());
  }

  public void visitEmptyStatement(EmptyStatementTree tree) {
    // no subtrees
  }

  public void visitLabelledStatement(LabelledStatementTree tree) {
    scan(tree.label());
    scan(tree.statement());
  }

  public void visitExpressionStatement(ExpressionStatementTree tree) {
    scan(tree.expression());
  }

  public void visitIfStatement(IfStatementTree tree) {
    scan(tree.condition());
    scan(tree.thenStatement());
    scan(tree.elseClause());
  }

  public void visitElseClause(ElseClauseTree tree) {
    scan(tree.statement());
  }

  public void visitForStatement(ForStatementTree tree) {
    scan(tree.init());
    scan(tree.condition());
    scan(tree.update());
    scan(tree.statement());
  }

  public void visitForInStatement(ForInStatementTree tree) {

  }

  public void visitForOfStatement(ForOfStatementTree tree) {
    scan(tree.variableOrExpression());
    scan(tree.expression());
    scan(tree.statement());
  }

  public void visitWhileStatement(WhileStatementTree tree) {
   scan(tree.condition());
   scan(tree.statement());
  }

  public void visitDoWhileStatement(DoWhileStatementTree tree) {
    scan(tree.statement());
    scan(tree.condition());
  }

  public void visitContinueStatement(ContinueStatementTree tree) {
    scan(tree.label());
  }

  public void visitIdentifier(IdentifierTree tree) {

  }

  public void visitBreakStatement(BreakStatementTree tree);

  public void visitReturnStatement(ReturnStatementTree tree);

  public void visitWithStatement(WithStatementTree tree);

  public void visitSwitchStatement(SwitchStatementTree tree);

  public void visitThrowStatement(ThrowStatementTree tree);

  public void visitTryStatement(TryStatementTree tree);

  public void visitDebugger(DebuggerStatementTree tree);

  public void visitArrayBindingPattern(ArrayBindingPatternTree tree);

  public void visitObjectLiteral(ObjectLiteralTree tree);

  public void visitBindingProperty(BindingPropertyTree tree);

  public void visitInitializedBindingElement(InitializedBindingElementTree tree);

  public void visitLiteral(LiteralTree tree);

  public void visitArrayLiteral(ArrayLiteralTree tree);

  public void visitAssignmentExpression(AssignmentExpressionTree tree);

  public void visitConditionalExpression(ConditionalExpressionTree tree);

  public void visitArrowFunction(ArrowFunctionTree tree);

  public void visitYieldExpression(YieldExpressionTree tree);

  public void visitBinaryExpression(BinaryExpressionTree tree);

  public void visitUnaryExpression(UnaryExpressionTree tree);

  public void visitBracketMemberExpression(BracketMemberExpressionTree tree);

  public void visitDotMemberExpression(DotMemberExpressionTree tree);

  public void visitTaggedTemplate(TaggedTemplateTree tree);

  public void visitCallExpression(CallExpressionTree tree);

  public void visitTemplateLiteral(TemplateLiteralTree tree);

  public void visitTemplateExpression(TemplateExpressionTree tree);

  public void visitParenthesisedExpression(ParenthesisedExpressionTree tree);

  public void visitComputedPropertyName(ComputedPropertyNameTree tree);

  public void visitPairProperty(PairPropertyTree tree);

  public void visitNewExpression(NewExpressionTree tree);

  public void visitThisTree(ThisTree tree);

  public void visitFunctionExpression(FunctionExpressionTree tree);
}
