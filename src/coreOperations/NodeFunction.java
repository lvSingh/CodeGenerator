package coreOperations;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;

public interface NodeFunction
{
    void execute(Node node) throws XPathExpressionException;
}
