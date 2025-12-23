package lox;

public class AstPrinter implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override public String visitLogicalExpr(Expr.Logical expr) {
        return " ";
    }

    @Override public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }
    @Override public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }

    @Override public String visitVariableExpr(Expr.Variable expr) {
        return expr.name.lexeme;
    }

    @Override public String visitAssignExpr(Expr.Assign expr) {
        return "";
    }

    private String parenthesize(String name, Expr... exprs){
        StringBuilder builder = new StringBuilder();

        builder.append("(");
        builder.append(name);
        for(Expr expr : exprs){
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");
        return builder.toString();

    }
}

class RpnPrinter implements Expr.Visitor<String> {
    @Override public String visitBinaryExpr(Expr.Binary expr) {
        return expr.left.accept(this) + " " + expr.right.accept(this) + " " + expr.operator.lexeme;
    }

    @Override public String visitLogicalExpr(Expr.Logical expr) {
        return null;
    }

    @Override public String visitGroupingExpr(Expr.Grouping expr) {
        return expr.expression.accept(this);
    }

    @Override public String visitLiteralExpr(Expr.Literal expr) {
        return expr.value.toString();
    }

    @Override public String visitVariableExpr(Expr.Variable expr) {
        return null;
    }
    @Override public String visitAssignExpr(Expr.Assign expr) {
        return null;
    }

    @Override public String visitUnaryExpr(Expr.Unary expr) {
        String operator =  expr.operator.lexeme;
        if (expr.operator.type == TokenType.MINUS){
            operator = "~";
        }

        return expr.right.accept(this) + " " + operator;
    }

    String print(Expr expr) {
        return expr.accept(this);
    }
}