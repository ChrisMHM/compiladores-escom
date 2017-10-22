run: salida
	./salida
salida: lex.yy.c instrucciones.tab.c
	gcc lex.yy.c instrucciones.tab.c -o salida -ly -ll -lm
instrucciones.tab.c:
	bison -d instrucciones.y
lex.yy.c:
	flex instrucciones.l
clean:
	rm lex.yy.c
	rm instrucciones.tab.c
	rm instrucciones.tab.h
	rm salida
	clear 

