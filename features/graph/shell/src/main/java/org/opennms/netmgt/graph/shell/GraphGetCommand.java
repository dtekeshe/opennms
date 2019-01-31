/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2019-2019 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2019 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.graph.shell;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.opennms.netmgt.graph.api.Edge;
import org.opennms.netmgt.graph.api.Graph;
import org.opennms.netmgt.graph.api.Vertex;
import org.opennms.netmgt.graph.api.generic.GenericGraph;
import org.opennms.netmgt.graph.api.service.GraphService;

@Command(scope = "graph", name = "get", description="Gets a graph identified by its namespace")
@Service
// TODO MVR expose via xml (graphml) or json
public class GraphGetCommand implements Action {

    @Reference
    private GraphService graphService;

    @Option(name="--container", description="The id of the container", required=true)
    private String containerId;

    @Option(name="--namespace", description="The namespace of the graph", required = true)
    private String namespace;

    // TODO MVR implement format
//    @Option(name="--format", description="The format to get the graph", required = false)
//    private String format; // json or graphml/xml

    @Override
    public Object execute() throws Exception {
        final Graph<Vertex, Edge> graph = graphService.getGraph(containerId, namespace);
        if (graph == null) {
            System.out.println("No graph with namespace " + namespace + " found");
        } else {
            final GenericGraph genericGraph = graph.asGenericGraph();
            System.out.println("Graph Details:");
            genericGraph.getProperties().forEach((key, value) -> System.out.println("  " + key + " => " + value));
            System.out.println();
            if (genericGraph.getVertices().isEmpty()) {
                System.out.println("No Vertices");
            } else {
                System.out.println("Vertex Details (" + genericGraph.getVertices().size() + ")");
                genericGraph.getVertices().forEach(v -> {
                    v.asGenericVertex().getProperties().forEach((key, value) -> System.out.println("  " + key + " => " + value));
                });
            }
            System.out.println();
            if (genericGraph.getEdges().isEmpty()) {
                System.out.println("No Edges");
            } else {
                System.out.println("Edge Details (" + genericGraph.getEdges().size() + ")");
                genericGraph.getEdges().forEach(e -> {
                    System.out.println(e.getSource().getId() + ":" + e.getSource().getNamespace() + " -> " + e.getTarget().getId() + ":" + e.getTarget().getNamespace());
                    e.asGenericEdge().getProperties().forEach((key, value) -> System.out.println("  " + key + " => " + value));
                });
            }
            System.out.println();
            System.out.println("Warning: This command is not fully implemented");
        }
        return null;
    }
}