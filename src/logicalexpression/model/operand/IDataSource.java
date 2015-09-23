/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicalexpression.model.operand;

/**
 *
 * @author Shulander
 */
public interface IDataSource {
    public Comparable<?> getValue(String key);
}
