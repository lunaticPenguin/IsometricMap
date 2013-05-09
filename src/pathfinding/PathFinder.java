package pathfinding;

import java.util.HashSet;

public class PathFinder {
	
	protected HashSet<Node> openedList;
	protected HashSet<Node> closedList;
	
	
	
	public void computePath(Node startNode, Node endNode) {
		
		/*
		 * 
		 * Liste ouverte : liste des noeuds étudiés
		 * Liste fermée : liste des noeuds solutions
		 * 
		 * -> Chaque point étudié est mis dans la liste ouverte et le meilleur de cette liste
		 * passe dans la liste fermée, il va servir de base pour la recherche suivante
		 * 
		 * -> A chq itération il ne faut pas uniquement chercher celui qui a la meilleure qualité dans ses voisins, 
		 * mais également dans toute la liste ouverte.
		 * 
		 * -> A chq itération, on va regarder parmi tous les noeuds qui ont été étudiés (et qui n'ont pas encore été choisis) 
		 * celui qui a la meilleure qualité.
		 * 
		 * -> L'algorithme s'arrête quand la destination a été atteinte ou bien lorsque toutes les solutions mises de côté ont été
		 * étudiées et qu'aucune ne s'est révélée bonne, c'est le cas où il n'y a pas de solution.
		 * 
		 * -> avant de se lancer dans l'étude de la qualité de chacun des noeuds adjacents, il ne faut prendre que ceux qui sont vraiment utilisables. 
		 * Il faut aussi mettre de côté tous les noeuds déjà présents dans la liste ouverte ou dans la liste fermée. 
		 * Et pour être plus précis, je dirais qu'il ne faut pas prendre un point s'il est déjà dans la liste ouverte, à moins qu'il ne soit meilleur, 
		 * auquel cas on va mettre à jour la liste ouverte.
		 * 
		 * ----------------------------
		 * 
		 * Pr chq noeud :
		 * -> Est-ce un obstacle ? si oui, on oublie ce noeud.
		 * 
		 * -> Est-il dans la liste fermée ? si oui, ce noeud a déjà été étudié ou bien est en cours d'étude, on ne fait rien.
		 * 
		 * -> Est-il dans la liste ouverte ? si oui, on calcule la qualité de ce noeud, et si elle est meilleure que celle de son
		 * homologue dans la liste ouverte, on modifie le noeud présent dans la liste ouverte.
		 * 
		 * -> Sinon, on l'ajoute dans la liste ouverte et on calcule sa qualité.
		 * 
		 */
	}
}
