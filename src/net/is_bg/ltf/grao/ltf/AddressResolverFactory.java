package net.is_bg.ltf.grao.ltf;

import grao.integration.structure.iface.IAddressResolver;
import grao.integration.structure.iface.IAddressResolverFactory;

/***
 * A factory that provides initialized instance of addressTree!
 * @author Lubo
 *
 */
public class AddressResolverFactory implements IAddressResolverFactory{

	@Override
	public IAddressResolver getAddressResolver() {
		IAddressResolver atree = AddressTree.getAddressResolver();
		return atree;
	}
}
