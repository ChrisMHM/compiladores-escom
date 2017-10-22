/** DEFINICIONES DE C O FLEX **/
%{
int lineas = 1;
	void imp_pal(FILE *,char *);
	enum{DIGITO = 128,ERROR,ID_MAL,COMENTARIOS,CADENAS, KEYWORD, TIPOSDATO, DIRECTIVAS, LIBS, OPERADORES, SEPARADORES, ID, NUMERICOS};
%}

/*	Definimos las expresiones regulares que serviran para identificar
 *	elementos del lenguaje C
 */	
DIGITO    [0-9]
KEYWORDS "auto"|"break"|"case"|"const"|"continue"|"default"|"do"|"else"|"enum"|"extern"|"for"|"goto"|"if"|"register"|"return"|"signed"|"sizeof"|"static"|"typedef"|"union"|"unsigned"|"volatile"|"while"
TIPOSDATO "char"|"int"|"long"|"short"|"float"|"double"|"void"
DIRECTIVAS	"#"[a-zA-Z]+
LIBS	"<"[a-z]+("."[a-zA-Z]+|"")">"
OPERADORES_ARITMETICOS ("+"|"-"|"*"|"/"|"%")|"="|("+"|"-"|"*"|"/"|"%")"="|"++"|"--"
OPERADORES_COMPARACION "<"|">"|">="|"<="|"!="|"=="|"!"|"&&"|"||"
OPERADORES_BIT ">>"|">>="|"<<"|"<<="|"~"|"&"|"&="|"|"|"|="|"^"|"^="
OPERADORES_OTROS ":"|"::"|"?"|"\\"
OPERADORES	{OPERADORES_ARITMETICOS}|{OPERADORES_COMPARACION}|{OPERADORES_BIT}|{OPERADORES_OTROS}
SEPARADORES "{"|"}"|"["|"]"|"("|")"|";"|","
ID       ([a-zA-Z]|"_")([a-zA-Z0-9]|"_")*
ID_MAL {DIGITO}+([a-zA-Z]|"_")([a-zA-Z0-9]|"_")*
NUMERICOS {DIGITO}+"."{DIGITO}+|{DIGITO}+
SECUENCIAS_ESCAPE "\\""\""|"\\""n"
CARACTERES_ESPECIALES "."|" "|"'"|"#"|"?"|":"|"\\"|"@"
CHARS "'"({SECUENCIAS_ESCAPE}|[a-zA-Z0-9]|{CARACTERES_ESPECIALES}|{SEPARADORES}|{OPERADORES}|{ID}|{KEYWORDS}|{TIPOSDATO}|{LIBS}|{DIRECTIVAS}|{NUMERICOS})*"'"
STRINGS "\""({SECUENCIAS_ESCAPE}|[a-zA-Z0-9]|{CARACTERES_ESPECIALES}|{SEPARADORES}|{OPERADORES}|{ID}|{KEYWORDS}|{TIPOSDATO}|{LIBS}|{DIRECTIVAS}|{NUMERICOS})*"\""
CADENAS {STRINGS}|{CHARS}
COMENTARIOS_UNALINEA "/""*"+([a-zA-Z0-9]|{CARACTERES_ESPECIALES}|{SEPARADORES}|{OPERADORES}|{ID}|{KEYWORDS}|{TIPOSDATO}|{CADENAS}|{LIBS}|{DIRECTIVAS}|{NUMERICOS}|{SECUENCIAS_ESCAPE})*"*"+"/"
COMENTARIOS_CONTINUACION ("/"|"")"*"([a-zA-Z0-9]|{CARACTERES_ESPECIALES}|{SEPARADORES}|{OPERADORES}|{ID}|{KEYWORDS}|{TIPOSDATO}|{CADENAS}|{LIBS}|{DIRECTIVAS}|{NUMERICOS}|{SECUENCIAS_ESCAPE})*
COMENTARIOS {COMENTARIOS_UNALINEA}|{COMENTARIOS_CONTINUACION}

%%
	/* EXPRESIONES REGULARES */
	
	
{COMENTARIOS}	{ return COMENTARIOS;}
{NUMERICOS}	{ return NUMERICOS; }
{CADENAS}	{ return CADENAS; }
{ID_MAL}     {return ERROR;}
{DIRECTIVAS}	{ return DIRECTIVAS; }
		
{LIBS}	        { return LIBS; }
{KEYWORDS}	{ return KEYWORD; }

{TIPOSDATO}	{ return TIPOSDATO; }

{ID}	{ return ID; }

{OPERADORES} { return OPERADORES; }

{SEPARADORES} { return SEPARADORES; }

"{"[\^{}}\n]*"}" { }

[\t]

[\n]	{lineas++; }     

" "	{ }

. 		{ return ERROR; }

%%

	/* CODIGO AUXILIAR */

int main( int argc, char **argv )
	{	
		int elToken;
		FILE * fsalida;
		FILE * ferror;

		if ( argc > 3 ){
			yyin = fopen(argv[1], "r");
			fsalida = fopen(argv[2] , "w");
			ferror = fopen(argv[3] , "w");
		}
		
		elToken = yylex();
		while(elToken){
			switch(elToken){
				case KEYWORD:
					imp_pal(fsalida,strdup("KEYWORD"));
					break;
				case COMENTARIOS:
					imp_pal(fsalida,strdup("COMENTARIO"));
					break;
				case NUMERICOS:
					imp_pal(fsalida,strdup("NUMERICO"));
					break;
				case CADENAS:
					imp_pal(fsalida,strdup("CADENA"));
					break;
				case DIRECTIVAS:
					imp_pal(fsalida,strdup("DIRECTIVA"));
					break;
				case LIBS:
					imp_pal(fsalida,strdup("LIBRERIA"));
					break;
				case TIPOSDATO:
					imp_pal(fsalida,strdup("TIPO_DATO"));
					break;
				case ID:
					imp_pal(fsalida,strdup("ID"));
					break;
				case OPERADORES:
					imp_pal(fsalida,strdup("OPERADOR"));
					break;
				case SEPARADORES:
					imp_pal(fsalida,strdup("SEPARADOR"));
					break;			 
				case ERROR:
					imp_pal(ferror,strdup("error"));
					break;
			}
			elToken = yylex();
		}
		return 0;
	}
	
	void imp_pal(FILE * fsalida,char *tipo){
		if(strcmp(yytext,"=")==0)
		    fprintf(fsalida, "%s \t%s \tAsignacion\n",tipo,yytext);
		else if(strcmp(yytext,"<")==0)
		    fprintf(fsalida, "%s \t%s \tMenor que\n",tipo,yytext);
		else if(strcmp(yytext,">")==0)
		    fprintf(fsalida, "%s \t%s \tMayor que\n",tipo,yytext);
		else if(strcmp(yytext,"++")==0)
		    fprintf(fsalida, "%s \t%s \tIncremento\n",tipo,yytext);
		else if(strcmp(yytext,"--")==0)
		    fprintf(fsalida, "%s \t%s \tDecremento\n",tipo,yytext);
		else if(strcmp(yytext,"-")==0)
		    fprintf(fsalida, "%s \t%s \tResta\n",tipo,yytext);
		else if(strcmp(yytext,"-=")==0)
		    fprintf(fsalida, "%s \t%s \tResta\n",tipo,yytext);
		else if(strcmp(yytext,"*=")==0)
		    fprintf(fsalida, "%s \t%s \tProducto\n",tipo,yytext);
		else if(strcmp(yytext,"+=")==0)
		    fprintf(fsalida, "%s \t%s \tSuma\n",tipo,yytext);
		else if(strcmp(yytext,"+")==0)
		    fprintf(fsalida, "%s \t%s \tSuma\n",tipo,yytext);
		else if(strcmp(yytext,"/")==0)
		    fprintf(fsalida, "%s \t%s \tDivision\n",tipo,yytext);
		else if(strcmp(yytext,"/=")==0)
		    fprintf(fsalida, "%s \t%s \tDivision\n",tipo,yytext);
		else if(yytext[0]==0x0d)
		    return;
		else if(strcmp(tipo,"error")==0)
		    fprintf(fsalida, "Linea: %d \t%s \n",lineas,yytext);
		else
		    fprintf(fsalida, "%s \t%s \t%s\n",tipo,yytext,yytext);
	}

