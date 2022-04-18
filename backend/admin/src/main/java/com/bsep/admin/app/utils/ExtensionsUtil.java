package com.bsep.admin.app.utils;

import com.bsep.admin.app.dto.ExtensionDto;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.x509.*;
import org.springframework.security.crypto.codec.Hex;

import java.io.IOException;
import java.util.ArrayList;

public class ExtensionsUtil {


    private static String keyIdentifierDisplay(String key) {
        return "Key Identifier: " + key + "\n";
    }

    private static String keyUsageDisplay(KeyUsage keyUsage) {

        String usages = "";
        if (keyUsage.hasUsages(1)) {
            usages += "Encipher Only" + "\n";
        }
        if (keyUsage.hasUsages(2)) {
            usages += "CRL Sign" + "\n";
        }
        if (keyUsage.hasUsages(8)) {
            usages += "Key Agreement" + "\n";
        }
        if (keyUsage.hasUsages(16)) {
            usages += "Data Encipherment" + "\n";
        }
        if (keyUsage.hasUsages(32)) {
            usages += "Key Encipherment" + "\n";
        }
        if (keyUsage.hasUsages(64)) {
            usages += "Non Repudiation" + "\n";
        }
        if (keyUsage.hasUsages(128)) {
            usages += "Digital Signature" + "\n";
        }
        if (keyUsage.hasUsages(32768)) {
            usages += "Decipher Only" + "\n";
        }
        if (keyUsage.hasUsages(4)) {
            usages += "Certificate Signing" + "\n";
        }

        return usages;
    }

    private static String generalName(int value) {

        if (value == 1) {
            return "RFC 822 Name";
        } else if (value == 2) {
            return "DNS Name";
        } else if (value == 4) {
            return "Directory Name";
        } else if (value == 6) {
            return "URI";
        } else if (value == 7) {
            return "IP Address";
        } else if (value == 8) {
            return "Registered ID";
        }
        return "Other";

    }

    private static String generalNamesDisplay(GeneralNames generalNames) {
        String display = "";

        GeneralName[] names = generalNames.getNames();
        for (GeneralName name : names) {
            display += "    " + generalName(name.getTagNo()) + ": " + name.getName().toString() + "\n";
        }
        return display;
    }

    private static String authorityKeyIdentifierDisplay(AuthorityKeyIdentifier authorityKeyIdentifier) {
        String display = keyIdentifierDisplay(new String(Hex.encode(authorityKeyIdentifier.getKeyIdentifier())));
        display += "Certificate Issuer: " + "\n" + generalNamesDisplay(authorityKeyIdentifier.getAuthorityCertIssuer());
        display += "Cerificate Serial Number: " + new String(authorityKeyIdentifier.getAuthorityCertSerialNumber().toByteArray());
        return display;
    }

    private static String basicConstraintsDisplay(BasicConstraints basicConstraints) {
        if (basicConstraints.isCA()) {
            return "Subject is a CA";
        } else {
            return "Subject is not a CA";
        }
    }

    public static String extendedKeyName(String id) {
        if (id.equals("1.3.6.1.5.5.7.3.1")) {
            return "TLS Web Server Authentication";
        } else if (id.equals("1.3.6.1.5.5.7.3.2")) {
            return "TLS Web Client Authentication";
        } else if (id.equals("1.3.6.1.5.5.7.3.3")) {
            return "Code Signing";
        } else if (id.equals("1.3.6.1.4.1.311.10.3.12")) {
            return "Document Signing";
        } else if (id.equals("1.2.840.113583.1.1.5")) {
            return "Adobe PDF Signing";
        } else if (id.equals("0.4.0.2231.3.0")) {
            return "TSL Signing";
        } else if (id.equals("1.3.6.1.5.5.7.3.4")) {
            return "E-mail Protection";
        } else if (id.equals("1.3.6.1.4.1.311.10.3.4")) {
            return "Encrypted File System";
        } else if (id.equals("1.3.6.1.5.5.7.3.5")) {
            return "IP Security End System";
        } else if (id.equals("1.3.6.1.5.5.7.3.6")) {
            return "IP Security Tunnel Termination";
        } else if (id.equals("1.3.6.1.5.5.7.3.7")) {
            return "IP Security User";
        } else if (id.equals("1.3.6.1.5.5.7.3.8")) {
            return "Time Stamping";
        } else if (id.equals("1.3.6.1.5.5.7.3.9")) {
            return "OCSP Signing";
        } else if (id.equals("1.3.6.1.4.1.311.20.2.2")) {
            return "Smartcard Logon";
        } else if (id.equals("2.5.29.37.0")) {
            return "Any Extended Key Usage";
        } else {
            return "";
        }

    }

    private static String extendedKeyUsageDisplay(ExtendedKeyUsage extendedKeyUsage) {
        String display = "";

        KeyPurposeId[] keys = extendedKeyUsage.getUsages();
        for (KeyPurposeId key : keys) {
            display += extendedKeyName(key.getId()) + "  (" + key.toOID() + ")" + "\n";
        }
        return display;
    }

    private static String policyConstraintsDisplay(PolicyConstraints policyConstraints) {
        String display = "Require Explicit Policy Skip Certificates: " + policyConstraints.getRequireExplicitPolicyMapping() + "\n";
        display += "Inhibit Policy Mapping Skip Certificates: " + policyConstraints.getInhibitPolicyMapping();
        return display;
    }

    private static String nameConstraintsDisplay(NameConstraints nameConstraints) {
        String display = "Permitted Subtrees: " + "\n";

        GeneralSubtree[] permittedSubtrees = nameConstraints.getPermittedSubtrees();
        for (GeneralSubtree peGeneralSubtree : permittedSubtrees) {
            display += "    Permitted Subtree: " + "\n" + "        Base: " + "\n" + "           " + generalName(peGeneralSubtree.getBase().getTagNo()) + ": "
                    + peGeneralSubtree.getBase().getName().toString() + "\n" + "        Minimum: " + peGeneralSubtree.getMinimum().toString()
                    + "\n" + "        Maximum: " + peGeneralSubtree.getMaximum().toString() + "\n";
        }
        display += "Excluded Subtrees: " + "\n";
        GeneralSubtree[] excludedSubtrees = nameConstraints.getExcludedSubtrees();
        for (GeneralSubtree excludedSubtree : excludedSubtrees) {
            display += "    Excluded Subtree: " + "\n" + "        Base: " + "\n" + "          " + generalName(excludedSubtree.getBase().getTagNo()) + ": "
                    + excludedSubtree.getBase().getName().toString() + "\n" + "        Minimum: " + excludedSubtree.getMinimum().toString()
                    + "\n" + "        Maximum: " + excludedSubtree.getMaximum().toString() + "\n";

        }
        return display;
    }

    public static ArrayList<ExtensionDto> convertToMap(Extensions extensions) throws IOException {
        ArrayList<ExtensionDto> list = new ArrayList<>();
        ASN1ObjectIdentifier[] identifiers = extensions.getExtensionOIDs();

        for (ASN1ObjectIdentifier identifier : identifiers) {
            if (identifier == Extension.subjectKeyIdentifier) {
                ASN1OctetString extensionValue = extensions.getExtension(Extension.subjectKeyIdentifier).getExtnValue();
                SubjectKeyIdentifier subjectKeyIdentifier = SubjectKeyIdentifier.getInstance(extensionValue.getOctets());
                ExtensionDto subjectKeyIdentifierExtension = new ExtensionDto("Subject Key Identifier", keyIdentifierDisplay(new String(Hex.encode(subjectKeyIdentifier.getKeyIdentifier()))));
                list.add(subjectKeyIdentifierExtension);
            } else if (identifier == Extension.keyUsage) {
                ASN1OctetString extensionValue = extensions.getExtension(Extension.keyUsage).getExtnValue();
                KeyUsage keyUsage = KeyUsage.getInstance(extensionValue.getOctets());
                ExtensionDto keyUsageExtension = new ExtensionDto("Key Usage", keyUsageDisplay(keyUsage));
                list.add(keyUsageExtension);
            } else if (identifier == Extension.authorityKeyIdentifier) {
                ASN1OctetString extensionValue = extensions.getExtension(Extension.authorityKeyIdentifier).getExtnValue();
                AuthorityKeyIdentifier authorityKeyIdentifier = AuthorityKeyIdentifier.getInstance(extensionValue.getOctets());
                ExtensionDto authorityKeyIdentifierExtension = new ExtensionDto("Authority Key Identifier", authorityKeyIdentifierDisplay(authorityKeyIdentifier));
                list.add(authorityKeyIdentifierExtension);
            } else if (identifier == Extension.subjectAlternativeName) {
                ASN1OctetString extensionValue = extensions.getExtension(Extension.subjectAlternativeName).getExtnValue();
                GeneralNames generalNames = GeneralNames.getInstance(extensionValue.getOctets());
                ExtensionDto subjectAlternativeNameExtension = new ExtensionDto("Subject Alternative Name", generalNamesDisplay(generalNames));
                list.add(subjectAlternativeNameExtension);
            } else if (identifier == Extension.basicConstraints) {
                ASN1OctetString extensionValue = extensions.getExtension(Extension.basicConstraints).getExtnValue();
                BasicConstraints basicConstraints = BasicConstraints.getInstance(extensionValue.getOctets());
                ExtensionDto basicConstraintsExtension = new ExtensionDto("Basic Constraints", basicConstraintsDisplay(basicConstraints));
                list.add(basicConstraintsExtension);
            } else if (identifier == Extension.extendedKeyUsage) {
                ASN1OctetString extensionValue = extensions.getExtension(Extension.extendedKeyUsage).getExtnValue();
                ExtendedKeyUsage extendedKeyUsage = ExtendedKeyUsage.getInstance(extensionValue.getOctets());
                ExtensionDto extendedKeyUsageExtension = new ExtensionDto("Extended Key Usage", extendedKeyUsageDisplay(extendedKeyUsage));
                list.add(extendedKeyUsageExtension);
            } else if (identifier == Extension.policyConstraints) {
                ASN1OctetString extensionValue = extensions.getExtension(Extension.policyConstraints).getExtnValue();
                PolicyConstraints policyConstraints = PolicyConstraints.getInstance(extensionValue.getOctets());
                ExtensionDto policyConstraintsExtension = new ExtensionDto("Policy Constraints", policyConstraintsDisplay(policyConstraints));
                list.add(policyConstraintsExtension);
            } else if (identifier == Extension.issuerAlternativeName) {
                //ASN1OctetString extensionValue = extensions.getExtension(Extension.issuerAlternativeName).getExtnValue();
                //Extension extension = Extension.getInstance(extensionValue.getOctets());
                //GeneralNames generalNames = GeneralNames.getInstance(issuerAlternativeName.getEncoded());
                //ExtensionDto issuerAlternativeNameExtension = new ExtensionDto("Issuer Alternative Name",generalNamesDisplay(generalNames));
                //list.add(issuerAlternativeNameExtension);

            } else if (identifier == Extension.nameConstraints) {
                ASN1OctetString extensionValue = extensions.getExtension(Extension.nameConstraints).getExtnValue();
                NameConstraints nameConstraints = NameConstraints.getInstance(extensionValue.getOctets());
                ExtensionDto nameConstraintsExtension = new ExtensionDto("Name Constraints", nameConstraintsDisplay(nameConstraints));
                list.add(nameConstraintsExtension);
            }
        }


        return list;
    }
}
