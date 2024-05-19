grammar FilterQuery ;

options
{
  language = Java;
}

@header
{
    package com.github.kotooriiii.myworld.util.antlr.grammar;
}

query : expression* EOF;

expression
 : expression WHITESPACE+? AND WHITESPACE+? expression                  #logicalAndExp
 | expression WHITESPACE+? OR WHITESPACE+? expression                   #logicalOrExp
 | attrname WHITESPACE+? conditionalOperator WHITESPACE+? value         #conditionalExp
 | WHITESPACE* '(' WHITESPACE*? expression WHITESPACE*? ')'             #groupExp
 ;

attrname : ATTNAME;

value
  : BOOLEAN         #boolean
  | NULL            #null
  | STRING          #string
  | DOUBLE          #double
  | LONG            #long
  ;

conditionalOperator : EQ | NE | GT | LT | GE | LE | CO | NC | SW | EW;

EQ : [eE][qQ];
NE : [nN][eE];
GT : [gG][tT];
LT : [lL][tT];
GE : [gG][eE];
LE : [lL][eE];
CO : [Cc][Oo];
NC : [Nn][Cc];
SW : [Ss][Ww];
EW : [Ee][Ww];

AND : [aA][nN][dD];
OR  : [oO][rR];

BOOLEAN : [tT][rR][uU][eE] | [fF][aA][lL][sS][eE];

NULL : [nN][uU][lL][lL];

STRING : '"' (ESC | ~ ["\\])* '"';

DOUBLE : '-'? INT '.' [0-9] + EXP?;

LONG : '-'? INT EXP | '-'? INT;

ATTNAME : [-_.:a-zA-Z0-9]+;

WHITESPACE : ' ';

fragment INT : '0' | [1-9] [0-9]*;

fragment EXP : [Ee] [+\-]? INT;

fragment ESC : '\\' (["\\/bfnrt] | UNICODE);

fragment UNICODE : 'u' HEX HEX HEX HEX ;
fragment HEX : [0-9a-fA-F] ;

