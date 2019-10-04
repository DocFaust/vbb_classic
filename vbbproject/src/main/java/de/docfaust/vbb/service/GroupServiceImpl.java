package de.docfaust.vbb.service;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Group;
import de.docfaust.vbb.data.facades.GroupFacade;

/**
 * Implementation of the GroupService.
 * 
 * @author wfa339
 *
 */
@Dependent
public class GroupServiceImpl implements GroupService {
	/**
	 * 
	 */
	private static final long serialVersionUID = -535962938015682213L;
	@EJB
	private GroupFacade groupFacade;
	@Inject
	private Logger logger;

	/**
	 * @param groupFacade groupFacade by JUnit
	 */
	public GroupServiceImpl(final GroupFacade groupFacade) {
		super();
		this.groupFacade = groupFacade;
		this.logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * 
	 */
	public GroupServiceImpl() {
		super();
	}

	@Override
	public List<Group> getGroups() {
		List<Group> list = groupFacade.findAll();
		logger.debug("Groups: {}", list.toString());
		return list;
	}

	@Override
	public Group findByName(final String name) {
		Group group = groupFacade.findByName(name);
		logger.debug("Found: {}", group.toString());
		return group;
	}

}
