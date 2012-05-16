package org.graphstream.netlogo.extension.graph;


import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.netlogo.extension.GSManager;
import org.nlogo.api.*;

/**
 * Implements the {@code add-node-to-graph} and {@code ang} commands.
 * 
 * Add a new node to the specified graph.
 * 
 * <pre>
 * gs:add-node-to-graph nodeName graphName
 * gs:ang nodeName graphName
 * </pre>
 * 
 * @param nodeName Can be a String (name of the node) or a Turtle (which id will become the name of the node)
 * @param graphName A String. The name of the graph in which the node will be added.
 * 
 * @author Fiegel
 */

public class AddNodeGraph extends DefaultReporter {
    
    @Override
    public String getAgentClassString() {
        return "O";
    }

    @Override
    public Syntax getSyntax() {
        int[] input = new int[] { Syntax.StringType() | Syntax.TurtleType(), Syntax.StringType() };
        int output = Syntax.BooleanType();
        
        return Syntax.reporterSyntax(input, output);
    }
    
    @Override
    public Object report(Argument[] args, Context context) throws ExtensionException {
        String nodeName, graphName;
        boolean status = true;
        
        try {
            Object arg = args[0].get();
            
            if(arg instanceof String) {
                nodeName = args[0].getString();
            }
            else {
                nodeName = "" + args[0].getTurtle().id();
            }
            
            graphName = args[1].getString();
        }
        catch(LogoException le) {
            throw new ExtensionException(le.getMessage());
        }
        
        try {
            GSManager.graphs.get(graphName).addNode(nodeName);
        }
        catch(IdAlreadyInUseException e) {
            status = false;
        }
        catch(NullPointerException e) {
            status = false;
        }
        
        return status;
    }
}
