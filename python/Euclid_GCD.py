def gcd_recursive(a, b):
    """Recursively finds GCD of a and b."""
    if b == 0:
        return a
    else:
        return gcd_recursive(b, a % b)

def gcd_iterative(a, b):
    """Iteratively finds GCD of a and b."""
    while b:
        a, b = b, a % b
    return a

# --- Main Execution ---
if __name__ == "__main__":
    num1 = 48
    num2 = 18
    
    print(f"GCD of {num1} and {num2} (Recursive): {gcd_recursive(num1, num2)}")
    print(f"GCD of {num1} and {num2} (Iterative): {gcd_iterative(num1, num2)}")