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
package org.sonar.plugins.javascript.api.tree.declaration;

import com.google.common.annotations.Beta;
import org.sonar.plugins.javascript.api.tree.expression.ExpressionTree;
import org.sonar.plugins.javascript.api.tree.lexical.SyntaxToken;
import org.sonar.plugins.javascript.api.tree.statement.BlockTree;

import javax.annotation.Nullable;

/**
 * Common interface for all types <a href="https://people.mozilla.org/~jorendorff/es6-draft.html#sec-method-definitions">Method Definitions</a> (<a href="http://wiki.ecmascript.org/doku.php?id=harmony:specification_drafts">ES6</a>).
 * <pre>
 *   {@link #name()} {@link #parameters()} {@link #body()}
 *   static {@link #name()} {@link #parameters()} {@link #body()}
 * </pre>
 */
@Beta
public interface MethodDeclarationTree extends DeclarationTree {

  @Nullable
  SyntaxToken staticToken();

  ExpressionTree name();

  ParameterListTree parameters();

  BlockTree body();

}
