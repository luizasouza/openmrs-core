package org.openmrs;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.serial.Item;
import org.openmrs.serial.Record;
import org.openmrs.serial.converter.julie.JulieConverter;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

/**
 * User in the system.  Both Patient and User inherit the methods of this class
 * 
 * @author Ben Wolfe
 * @version 2.0
 */
public class Person implements java.io.Serializable, JulieConverter {

	public static final Log log = LogFactory.getLog(Person.class);
	
	public static final long serialVersionUID = 13533L;

	protected Integer personId;
	
	@ElementList
	private Set<PersonAddress> addresses = null;
	private Set<PersonName> names = null;
	private Set<PersonAttribute> attributes = null;
	
	private String gender;
	private Date birthdate;
	private Boolean birthdateEstimated = false;
	@Attribute
	private Boolean dead = false;
	private Date deathDate;
	private Concept causeOfDeath;

	private User creator;
	private Date dateCreated;
	private User changedBy;
	private Date dateChanged;
	@Attribute(name="personvoided")
	private Boolean voided = false;
	private User voidedBy;
	private Date dateVoided;
	private String voidReason;
    private String guid;
	
	// convenience map
	Map<String, PersonAttribute> attributeMap = null;
	
	// default constructor
	public Person() {
	}
	
	public Person(Person person) {
		if (person == null)
			return;
		
		personId = person.getPersonId();
		addresses = person.getAddresses();
		names = person.getNames();
		attributes = person.getAttributes();
		
		gender = person.getGender();
		birthdate = person.getBirthdate();
		birthdateEstimated = person.getBirthdateEstimated();
		dead = person.isDead();
		deathDate = person.getDeathDate();
		causeOfDeath = person.getCauseOfDeath();
		
		creator = person.getCreator();
		dateCreated = person.getDateCreated();
		changedBy = person.getChangedBy();
		dateChanged = person.getDateChanged();
		voided = person.isVoided();
		voidedBy = person.getVoidedBy();
		dateVoided = person.getDateVoided();
		voidReason=  person.getVoidReason();
	}

	public Person(Integer personId) {
		this.personId = personId;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Person) {
			Person u = (Person) obj;
			return (getPersonId().equals(u.getPersonId()));
		}
		return false;
	}

	public int hashCode() {
		if (this.getPersonId() == null)
			return super.hashCode();
		return this.getPersonId().hashCode();
	}

	// Property accessors

	/**
	 * @return Returns the personId.
	 */
	public Integer getPersonId() {
		return personId;
	}

	/**
	 * @param personId
	 *            The personId to set.
	 */
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	
	/**
	 * @return person's gender
	 */
	public String getGender() {
		return this.gender;
	}

	/**
	 * @param gender
	 *            person's gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
	 * @return person's date of birth
	 */
	public Date getBirthdate() {
		return this.birthdate;
	}

	/**
	 * @param birthdate
	 *            person's date of birth
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * @return true if person's birthdate is estimated
	 */
	public Boolean isBirthdateEstimated() {
		// if (this.birthdateEstimated == null) {
		// return new Boolean(false);
		// }
		return this.birthdateEstimated;
	}

	public Boolean getBirthdateEstimated() {
		return isBirthdateEstimated();
	}

	/**
	 * @param birthdateEstimated
	 *            true if person's birthdate is estimated
	 */
	public void setBirthdateEstimated(Boolean birthdateEstimated) {
		this.birthdateEstimated = birthdateEstimated;
	}
	
	/**
	 * @return Returns the death status.
	 */
	public Boolean isDead() {
		return dead;
	}
	
	/**
	 * @return Returns the death status.
	 */
	public Boolean getDead() {
		return isDead();
	}

	/**
	 * @param dead The dead to set.
	 */
	public void setDead(Boolean dead) {
		this.dead = dead;
	}

	/**
	 * @return date of person's death
	 */
	public Date getDeathDate() {
		return this.deathDate;
	}

	/**
	 * @param deathDate
	 *            date of person's death
	 */
	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	/**
	 * @return cause of person's death
	 */
	public Concept getCauseOfDeath() {
		return this.causeOfDeath;
	}

	/**
	 * @param causeOfDeath
	 *            cause of person's death
	 */
	public void setCauseOfDeath(Concept causeOfDeath) {
		this.causeOfDeath = causeOfDeath;
	}
	
	/**
	 * @return list of known addresses for person
	 * @see org.openmrs.PersonAddress
	 */
	public Set<PersonAddress> getAddresses() {
		if (addresses == null)
			addresses = new HashSet<PersonAddress>();
		return this.addresses;
	}

	/**
	 * @param personAddresses
	 *            list of known addresses for person
	 * @see org.openmrs.PersonAddress
	 */
	public void setAddresses(Set<PersonAddress> addresses) {
		this.addresses = addresses;
	}

	/**
	 * @return all known names for person
	 * @see org.openmrs.PersonName
	 */
	public Set<PersonName> getNames() {
		if (names == null)
			names = new HashSet<PersonName>();
		return this.names;
	}

	/**
	 * @param names
	 *            update all known names for person
	 * @see org.openmrs.PersonName
	 */
	public void setNames(Set<PersonName> names) {
		this.names = names;
	}

	/**
	 * @return all known attributes for person
	 * @see org.openmrs.PersonAttribute
	 */
	public Set<PersonAttribute> getAttributes() {
		if (attributes == null)
			attributes = new HashSet<PersonAttribute>();
		return this.attributes;
	}

	/**
	 * Returns only the non-voided attributes for this person
	 * 
	 * @return list attributes
	 */
	public List<PersonAttribute> getActiveAttributes() {
		List<PersonAttribute> attrs = new Vector<PersonAttribute>();
		for (PersonAttribute attr : getAttributes()) {
			if (!attr.isVoided())
				attrs.add(attr);
		}
		return attrs;
	}
	
	/**
	 * @param attributes
	 *            update all known attributes for person
	 * @see org.openmrs.PersonAttribute
	 */
	public void setAttributes(Set<PersonAttribute> attributes) {
		this.attributes = attributes;
		attributeMap = null;
	}
	
	
	// Convenience methods
	
	/**
	 * Convenience Method
	 * Adds the <code>attribute</code> to this person's attribute list if the attribute doesn't exist already
	 * Voids any current attribute with type = <code>newAttribute.getAttributeType()</code>
	 * 
	 * ** NOTE: This effectively limits persons to only one attribute of any given type **
	 * 
	 * @param name
	 */
	public void addAttribute(PersonAttribute newAttribute) {
		newAttribute.setPerson(this);
		for (PersonAttribute currentAttribute : getActiveAttributes()) {
			if (currentAttribute.equals(newAttribute))
				return; // if we have the same PersonAttributeId, don't add the new attribute
			else if (currentAttribute.getAttributeType().equals(newAttribute.getAttributeType())) {
				if (currentAttribute.getValue() != null && currentAttribute.getValue().equals(newAttribute.getValue()))
					// this person already has this attribute
					return;
				
				// if we have the same type, different value, and the old one isn't voided already
				if (currentAttribute.getCreator() != null)
					currentAttribute.voidAttribute("New value: " + newAttribute.getValue());
				else
					// remove the attribute if it was just temporary (didn't have a creator attached to it yet)
					removeAttribute(currentAttribute);
			}
		}
		attributeMap = null;
		attributes.add(newAttribute);
	}

	/**
	 * Convenience Method
	 * Removes the <code>attribute</code> from this person's attribute list if the attribute exists already
	 * @param attribute
	 */
	public void removeAttribute(PersonAttribute attribute) {
		if (attributes != null)
			if (attributes.remove(attribute))
				attributeMap = null;
	}
	
	/**
	 * Convenience Method
	 * Returns this person's first attribute that has a PersonAttributeType.name equal to <code>attributeName</code>
	 * @param attribute
	 */
	public PersonAttribute getAttribute(String attributeName) {
		if (attributeName != null)
			for (PersonAttribute attribute : getActiveAttributes()) {
				PersonAttributeType type = attribute.getAttributeType();
				if (type != null && attributeName.equals(type.getName())) {
					return attribute;
				}
			}
		
		return null;
	}
	
	/**
	 * Convenience Method
	 * Returns this person's first attribute that has a PersonAttributeType.id equal to <code>attributeTypeId</code>
	 * @param attribute
	 */
	public PersonAttribute getAttribute(Integer attributeTypeId) {
		for (PersonAttribute attribute : getActiveAttributes()) {
			if (attributeTypeId.equals(attribute.getAttributeType().getPersonAttributeTypeId())) {
				return attribute;
			}
		}
		
		return null;
	}
	
	/**
	 * Convenience Method
	 * Returns all of this person's attributes that have a PersonAttributeType.name equal to <code>attributeName</code>
	 * @param attribute
	 */
	public List<PersonAttribute> getAttributes(String attributeName) {
		List<PersonAttribute> attributes = new Vector<PersonAttribute>();
		
		for (PersonAttribute attribute : getActiveAttributes()) {
			PersonAttributeType type = attribute.getAttributeType();
			if (type != null && attributeName.equals(type.getName())) {
				attributes.add(attribute);
			}
		}
		
		return attributes;
	}
	
	/**
	 * Convenience Method
	 * Returns all of this person's attributes that have a PersonAttributeType.id equal to <code>attributeTypeId</code>
	 * @param attribute
	 */
	public List<PersonAttribute> getAttributes(Integer attributeTypeId) {
		List<PersonAttribute> attributes = new Vector<PersonAttribute>();
		
		for (PersonAttribute attribute : getActiveAttributes()) {
			if (attributeTypeId.equals(attribute.getPersonAttributeId())) {
				attributes.add(attribute);
			}
		}
		
		return attributes;
	}
	
	/**
	 * Convenience Method
	 * Returns all of this person's attributes in map form: <String, PersonAttribute>
	 * 
	 * @param attribute
	 */
	public Map<String, PersonAttribute> getAttributeMap() {
		if (attributeMap != null)
			return attributeMap;
		
		if (log.isDebugEnabled())
			log.debug("Current Person Attributes: \n" + printAttributes());
		
		attributeMap = new HashMap<String, PersonAttribute>();
		for (PersonAttribute attribute : getActiveAttributes()) {
			attributeMap.put(attribute.getAttributeType().getName(), attribute);
		}
		
		return attributeMap;
	}
	
	/**
	 * Convenience method for viewing all of the person's current attributes
	 */
	public String printAttributes() {
		String s = "";
		
		for (PersonAttribute attribute : getAttributes()) {
			s += attribute.getAttributeType() + " : " + attribute.getValue() + " : voided? " + attribute.isVoided() + "\n";
		}
		
		return s;
	}
	
	/**
	 * Convenience Method
	 * Adds the <code>name</code> to this person's name list if the name doesn't exist already
	 * @param name
	 */
	public void addName(PersonName name) {
		name.setPerson(this);
		if (names == null)
			names = new HashSet<PersonName>();
		if (!names.contains(name) && name != null)
			names.add(name);
	}

	/**
	 * Convenience Method
	 * Removes the <code>name</code> from this person's name list if the name exists already
	 * @param name
	 */
	public void removeName(PersonName name) {
		if (names != null)
			names.remove(name);
	}

	/**
	 * Convenience Method
	 * Adds the <code>address</code> to this person's address list if the address doesn't exist already
	 * @param address
	 */
	public void addAddress(PersonAddress address) {
		address.setPerson(this);
		if (addresses == null)
			addresses = new HashSet<PersonAddress>();
		if (!addresses.contains(address) && address != null)
			addresses.add(address);
	}

	/**
	 * Convenience Method
	 * Removes the <code>address</code> from this person's address list if the address exists already
	 * @param address
	 */
	public void removeAddress(PersonAddress address) {
		if (addresses != null)
			addresses.remove(address);
	}
	
	/**
	 * Convenience Method 
	 * Get the "preferred" name for the person.
	 * 
	 * Returns a blank PersonName object if no names are given
	 * 
	 * @return Returns the "preferred" person name.
	 */
	public PersonName getPersonName() {
		if (names != null && names.size() > 0) {
			return (PersonName) names.toArray()[0];
		} else {
			return new PersonName();
		}
	}
	
	/**
	 * Convenience method to get the given name attribute on this
	 * person's preferred PersonName
	 * 
	 * @return String given name of the person
	 */
	public String getGivenName() {
		PersonName personName = getPersonName();
		if (personName == null)
			return "";
		else
			return personName.getGivenName();
	}
	
	/**
	 * Convenience method to get the middle name attribute on this
	 * person's preferred PersonName
	 * 
	 * @return String middle name of the person
	 */
	public String getMiddleName() {
		PersonName personName = getPersonName();
		if (personName == null)
			return "";
		else
			return personName.getMiddleName();
	}
	
	/**
	 * Convenience method to get the family name attribute on this
	 * person's preferred PersonName
	 * 
	 * @return String family name of the person
	 */
	public String getFamilyName() {
		PersonName personName = getPersonName();
		if (personName == null)
			return "";
		else
			return personName.getFamilyName();
	}
	
	/**
	 * Convenience Method
	 * To get the "preferred" address for person.
	 * 
	 * @return Returns the "preferred" person address.
	 */
	public PersonAddress getPersonAddress() {
		if (addresses != null && addresses.size() > 0) {
			return (PersonAddress) addresses.toArray()[0];
		} else {
			return null;
		}
	}
	
	/**
	 * Convenience Method
	 * Calculates person's age based on the birthdate
	 * 
	 * @return integer age
	 */
	public Integer getAge() {
		
		if (birthdate == null)
			return null;
		
		Calendar today = Calendar.getInstance();
		
		Calendar bday = new GregorianCalendar();
		bday.setTime(birthdate);
		
		int age = today.get(Calendar.YEAR) - bday.get(Calendar.YEAR);
		
		//tricky bit:
		// set birthday calendar to this year
		// if the current date is less that the new 'birthday', subtract a year
		bday.set(Calendar.YEAR, today.get(Calendar.YEAR));
		if (today.before(bday)) {
				age = age -1;
		}
		
		return age;
	}
	
	
	
	public User getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}

	public Date getDateChanged() {
		return dateChanged;
	}

	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateVoided() {
		return dateVoided;
	}

	public void setDateVoided(Date dateVoided) {
		this.dateVoided = dateVoided;
	}

	public void setVoided(Boolean voided) {
		this.voided = voided;
	}

	public Boolean getVoided() {
		return isVoided();
	}

	public Boolean isVoided() {
		return voided;
	}

	public User getVoidedBy() {
		return voidedBy;
	}

	public void setVoidedBy(User voidedBy) {
		this.voidedBy = voidedBy;
	}

	public String getVoidReason() {
		return voidReason;
	}

	public void setVoidReason(String voidReason) {
		this.voidReason = voidReason;
	}

	public String toString() {
		return "Person(personId=" + personId + ")";
	}

    /**
     * @see org.openmrs.serial.converter.julie.JulieConverter#load(org.openmrs.serial.Record, org.openmrs.serial.Item)
     */
    public void load(Record xml, Item me) throws Exception {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see org.openmrs.serial.converter.julie.JulieConverter#save(org.openmrs.serial.Record, org.openmrs.serial.Item)
     */
    public Item save(Record xml, Item parent) throws Exception {
        Item me = xml.createItem(parent, "person");
        me.setAttribute("personvoided", Boolean.toString(getVoided()));
        me.setAttribute("dead", Boolean.toString(getDead()));
        
        // could be moved to parent in stead
        if (getBirthdate() != null) {
            Item birthdateItem = xml.createItem(me, "birthdate");
            xml.createText(birthdateItem, getBirthdate().toString());
            birthdateItem.setAttribute("birthdateestimated", Boolean.toString(getBirthdateEstimated()));
        }
        
        // Doesn't handle circular references
        //getChangedBy().save(xml, me);
        
        return me;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String value) {
        this.guid = guid;
    }

}